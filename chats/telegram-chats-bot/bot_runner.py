#!/usr/bin/env python3
"""
Гибрид: бот принимает команды в Telegram, создание чатов выполняет твой аккаунт (Pyrogram).
Bot API не умеет создавать чаты — поэтому бот только принимает запросы,
а реально создаёт чаты твой аккаунт (user client).
"""

import asyncio
import os
import re

from pyrogram import Client, filters, idle
from pyrogram.enums import ParseMode

try:
    from dotenv import load_dotenv
    load_dotenv()
except ImportError:
    pass

# Конфиг
API_ID = int(os.environ.get("API_ID", "0"))
API_HASH = os.environ.get("API_HASH", "")
SESSION_NAME = os.environ.get("SESSION_NAME", "chats_bot_session")
BOT_TOKEN = os.environ.get("BOT_TOKEN", "")

# Импорт логики создания чатов из основного скрипта
from chats_bot import create_chats, get_today_date, parse_username_or_phone


# User client — твой аккаунт, им создаются чаты (запускаем и держим в памяти)
user_app = Client(
    SESSION_NAME,
    api_id=API_ID,
    api_hash=API_HASH,
)

# Bot client — принимает команды от пользователей
bot_app = Client(
    "chats_bot",
    api_id=API_ID,
    api_hash=API_HASH,
    bot_token=BOT_TOKEN,
)


@bot_app.on_message(filters.command("start"))
async def cmd_start(client: Client, message):
    await message.reply_text(
        "Привет. Создание чатов идёт через *твой аккаунт* (не через бота — Telegram так не умеет).\n\n"
        "Команды:\n"
        "• /create N — создать N чатов с названиями по умолчанию (дата + номер)\n"
        "• /create N @user1 +79001234567 — то же + добавить участников (ник или номер через пробел)\n"
        "• /date — показать сегодняшнюю дату\n\n"
        "Пример: /create 3 @alice +79001234567",
        parse_mode=ParseMode.MARKDOWN,
    )


@bot_app.on_message(filters.command("date"))
async def cmd_date(client: Client, message):
    await message.reply_text(f"Сегодня: **{get_today_date()}**", parse_mode=ParseMode.MARKDOWN)


@bot_app.on_message(filters.command("create"))
async def cmd_create(client: Client, message):
    if not message.text or not message.text.strip():
        return
    parts = message.text.strip().split()
    if len(parts) < 2:
        await message.reply_text("Использование: /create N [@user1 @user2 +79...]")
        return

    try:
        count = int(parts[1])
    except ValueError:
        await message.reply_text("Укажи число чатов, например: /create 5")
        return

    if count <= 0 or count > 20:
        await message.reply_text("Число чатов: от 1 до 20.")
        return

    # Участники: всё после второго аргумента
    members = []
    for raw in parts[2:]:
        member = parse_username_or_phone(raw)
        if member:
            members.append(member)

    chat_id = message.chat.id

    async def log_fn(msg: str):
        await client.send_message(chat_id, msg)

    await message.reply_text(
        f"Создаю {count} чат(ов), дата: {get_today_date()}. Участников: {len(members)}. Жди сообщения по шагам."
    )

    try:
        created = await create_chats(
            user_app,
            count,
            titles=None,
            members=members,
            log_fn=log_fn,
        )
        await message.reply_text(f"Готово. Создано чатов: {len(created)}.")
    except Exception as e:
        await message.reply_text(f"Ошибка: {e}")


async def main():
    if not all([API_ID, API_HASH, SESSION_NAME, BOT_TOKEN]):
        print("Нужны API_ID, API_HASH, SESSION_NAME и BOT_TOKEN в .env")
        return

    await user_app.start()
    await bot_app.start()

    me_bot = await bot_app.get_me()
    me_user = await user_app.get_me()
    print(f"Бот: @{me_bot.username}")
    print(f"Аккаунт для создания чатов: {me_user.first_name} (@{me_user.username or '—'})")
    print("Жду команды в боте...")

    await idle()

    await bot_app.stop()
    await user_app.stop()


if __name__ == "__main__":
    asyncio.run(main())
