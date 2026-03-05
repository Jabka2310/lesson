#!/usr/bin/env python3
"""
Телеграм-бот для создания чатов (супергрупп).
По запросу: создаёт N чатов, подтягивает дату, позволяет менять названия и добавлять участников.
Использует Pyrogram (user client).
"""

import asyncio
import os
import re
from datetime import datetime
from typing import List, Optional

from pyrogram import Client
from pyrogram.errors import FloodWait, RPCError

# Загрузка из .env
try:
    from dotenv import load_dotenv
    load_dotenv()
except ImportError:
    pass

API_ID = int(os.environ.get("API_ID", "0"))
API_HASH = os.environ.get("API_HASH", "")
SESSION_NAME = os.environ.get("SESSION_NAME", "chats_bot_session")


def get_today_date() -> str:
    """Сегодняшняя дата в формате для названий."""
    return datetime.now().strftime("%Y-%m-%d")


def default_chat_title(index: int) -> str:
    """Название чата по умолчанию: дата + номер."""
    return f"Чат {get_today_date()} #{index}"


def parse_username_or_phone(value: str) -> str:
    """Нормализует ввод: @username или +79991234567."""
    value = value.strip()
    if value.startswith("+"):
        return value
    if not value.startswith("@"):
        return f"@{value}" if value else value
    return value


async def create_chats(
    app: Client,
    count: int,
    titles: Optional[List[str]] = None,
    members: Optional[List[str]] = None,
    log_fn=None,
) -> List:
    """
    Создаёт count супергрупп с заданными названиями и добавляет участников.
    titles: список названий (если короче count — остальные по умолчанию).
    members: список @ник или +номер.
    log_fn(msg): опционально вызывается для каждого сообщения (для бота — отправка в чат).
    Возвращает список созданных Chat.
    """
    if titles is None:
        titles = []
    if members is None:
        members = []
    while len(titles) < count:
        titles.append(default_chat_title(len(titles) + 1))

    created_chats = []
    for i in range(count):
        title = titles[i]
        try:
            chat = await app.create_supergroup(title=title, description=f"Создано {get_today_date()}")
            created_chats.append(chat)
            msg = f"[{i + 1}/{count}] Создан чат: {chat.title} (id: {chat.id})"
            if log_fn:
                await log_fn(msg)
            else:
                print(msg)
        except FloodWait as e:
            wait_msg = f"Ожидание {e.value} сек (лимит Telegram)..."
            if log_fn:
                await log_fn(wait_msg)
            else:
                print(wait_msg)
            await asyncio.sleep(e.value)
            chat = await app.create_supergroup(title=title, description=f"Создано {get_today_date()}")
            created_chats.append(chat)
            msg = f"[{i + 1}/{count}] Создан чат: {chat.title} (id: {chat.id})"
            if log_fn:
                await log_fn(msg)
            else:
                print(msg)
        except RPCError as e:
            msg = f"[{i + 1}/{count}] Ошибка: {e}"
            if log_fn:
                await log_fn(msg)
            else:
                print(msg)
            continue

        for member in members:
            msg = f"  + Добавлен: {member}"
            try:
                await app.add_chat_members(chat.id, member)
                if log_fn:
                    await log_fn(msg)
                else:
                    print(msg)
            except FloodWait as e:
                await asyncio.sleep(e.value)
                await app.add_chat_members(chat.id, member)
                if log_fn:
                    await log_fn(msg)
                else:
                    print(msg)
            except RPCError as e:
                msg = f"  Не удалось добавить {member}: {e}"
                if log_fn:
                    await log_fn(msg)
                else:
                    print(msg)

        if i < count - 1:
            await asyncio.sleep(10)
    return created_chats


async def create_chats_flow(app: Client) -> None:
    """Интерактивный сценарий: количество чатов, названия, участники."""
    print("Дата сегодня:", get_today_date())
    print()

    try:
        count = int(input("Сколько чатов создать? (число): ").strip())
    except ValueError:
        print("Введено не число. Выход.")
        return
    if count <= 0:
        print("Число должно быть больше 0.")
        return

    # Спрашиваем названия для каждого чата (можно оставить пусто — будет по умолчанию)
    titles: List[str] = []
    for i in range(1, count + 1):
        default = default_chat_title(i)
        raw = input(f"Название чата {i} (Enter = '{default}'): ").strip()
        titles.append(raw if raw else default)

    # Участники: по нику или номеру телефона
    members_input = input(
        "Добавить участников? Введите через запятую (@ник или +79991234567): "
    ).strip()
    members = []
    if members_input:
        for part in re.split(r"[,;\s]+", members_input):
            part = parse_username_or_phone(part)
            if part:
                members.append(part)

    created_chats = await create_chats(app, count, titles=titles, members=members)

    # Редактирование названий созданных чатов
    if created_chats:
        rename = input("Изменить названия чатов? (y/n): ").strip().lower()
        if rename == "y":
            for i, chat in enumerate(created_chats):
                new_name = input(f"Новое название для '{chat.title}' (Enter — оставить): ").strip()
                if new_name:
                    try:
                        await app.set_chat_title(chat.id, new_name)
                        print(f"  Переименован в: {new_name}")
                    except RPCError as e:
                        print(f"  Ошибка переименования: {e}")

    print("Готово.")


async def main() -> None:
    if not API_ID or not API_HASH:
        print("Заполните API_ID и API_HASH в .env (получить на https://my.telegram.org)")
        return

    async with Client(SESSION_NAME, api_id=API_ID, api_hash=API_HASH) as app:
        me = await app.get_me()
        print(f"Вход выполнен: {me.first_name} (@{me.username or '—'})")
        print()
        await create_chats_flow(app)


if __name__ == "__main__":
    asyncio.run(main())
