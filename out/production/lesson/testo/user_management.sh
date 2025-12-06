#!/bin/bash

# Скрипт для управления пользователями и группами
# Требует запуск от имени root

# Проверка на запуск от root
if [[ $EUID -ne 0 ]]; then
   echo "Этот скрипт должен быть запущен от имени root!"
   exit 1
fi

echo "Начинаем настройку пользователей и групп..."

# 1. Создание группы default_users
echo "=== СОЗДАНИЕ ГРУППЫ DEFAULT_USERS ==="
if ! getent group default_users > /dev/null 2>&1; then
    groupadd default_users
    echo "Группа default_users создана"
else
    echo "Группа default_users уже существует"
fi

# 2. Создание пользователя user в группе default_users
echo "=== СОЗДАНИЕ ПОЛЬЗОВАТЕЛЯ USER ==="
if ! id "user" > /dev/null 2>&1; then
    useradd -m -g default_users -s /bin/bash user
    echo "Пользователь user создан в группе default_users"
else
    echo "Пользователь user уже существует"
    # Добавляем в группу если не состоит
    usermod -a -G default_users user
fi

# 3. Создание группы secret_users
echo "=== СОЗДАНИЕ ГРУППЫ SECRET_USERS ==="
if ! getent group secret_users > /dev/null 2>&1; then
    groupadd secret_users
    echo "Группа secret_users создана"
else
    echo "Группа secret_users уже существует"
fi

# 4. Создание пользователей secret_agent, secret_spy, secret_boss
echo "=== СОЗДАНИЕ СЕКРЕТНЫХ ПОЛЬЗОВАТЕЛЕЙ ==="
SECRET_USERS=("secret_agent" "secret_spy" "secret_boss")

for username in "${SECRET_USERS[@]}"; do
    if ! id "$username" > /dev/null 2>&1; then
        useradd -m -g secret_users -s /bin/bash "$username"
        echo "Пользователь $username создан в группе secret_users"
    else
        echo "Пользователь $username уже существует"
        # Добавляем в группу если не состоит
        usermod -a -G secret_users "$username"
    fi
done

# 5. Настройка прав доступа к домашним директориям
echo "=== НАСТРОЙКА ПРАВ ДОСТУПА К ДОМАШНИМ ДИРЕКТОРИЯМ ==="

# Устанавливаем права для пользователя user (только владелец)
chmod 700 /home/user
chown user:default_users /home/user
echo "Права для /home/user установлены: 700 (только владелец)"

# Устанавливаем права для секретных пользователей (группа secret_users может читать)
for username in "${SECRET_USERS[@]}"; do
    chmod 750 /home/"$username"
    chown "$username":secret_users /home/"$username"
    echo "Права для /home/$username установлены: 750 (владелец + группа)"
done

# 6. Настройка прав для каталога /var
echo "=== НАСТРОЙКА ПРАВ ДЛЯ /VAR ==="
chmod 755 /var
echo "Права для /var установлены: 755 (все могут читать и выполнять)"

# 7. Установка Apache2
echo "=== УСТАНОВКА APACHE2 ==="
if command -v apt-get &> /dev/null; then
    echo "Используем apt-get для установки Apache2..."
    apt-get update
    apt-get install -y apache2
elif command -v yum &> /dev/null; then
    echo "Используем yum для установки Apache2..."
    yum install -y httpd
elif command -v dnf &> /dev/null; then
    echo "Используем dnf для установки Apache2..."
    dnf install -y httpd
elif command -v pacman &> /dev/null; then
    echo "Используем pacman для установки Apache2..."
    pacman -S --noconfirm apache
else
    echo "Не удалось определить пакетный менеджер для установки Apache2"
fi

# 8. Проверка состояния Apache2
echo "=== ПРОВЕРКА СОСТОЯНИЯ APACHE2 ==="
if command -v systemctl &> /dev/null; then
    # Для systemd систем
    if systemctl is-active --quiet apache2; then
        echo "Apache2 запущен"
        systemctl status apache2 --no-pager -l
    elif systemctl is-active --quiet httpd; then
        echo "HTTPD запущен"
        systemctl status httpd --no-pager -l
    else
        echo "Apache2/HTTPD не запущен, запускаем..."
        systemctl start apache2 2>/dev/null || systemctl start httpd
        systemctl enable apache2 2>/dev/null || systemctl enable httpd
    fi
elif command -v service &> /dev/null; then
    # Для init.d систем
    if service apache2 status > /dev/null 2>&1; then
        echo "Apache2 запущен"
        service apache2 status
    elif service httpd status > /dev/null 2>&1; then
        echo "HTTPD запущен"
        service httpd status
    else
        echo "Apache2/HTTPD не запущен, запускаем..."
        service apache2 start 2>/dev/null || service httpd start
    fi
else
    echo "Не удалось проверить состояние Apache2"
fi

# 9. Настройка sudo для группы default_users
echo "=== НАСТРОЙКА SUDO ДЛЯ DEFAULT_USERS ==="

# Создаем резервную копию sudoers
cp /etc/sudoers /etc/sudoers.backup.$(date +%Y%m%d_%H%M%S)

# Проверяем, есть ли уже запись для default_users
if ! grep -q "default_users" /etc/sudoers; then
    # Добавляем запись в sudoers
    echo "%default_users ALL=(ALL) NOPASSWD: ALL" >> /etc/sudoers
    echo "Права sudo без пароля добавлены для группы default_users"
else
    echo "Права sudo для группы default_users уже настроены"
fi

# Проверяем синтаксис sudoers
if visudo -c; then
    echo "Синтаксис sudoers корректен"
else
    echo "ОШИБКА: Синтаксис sudoers некорректен! Восстанавливаем резервную копию..."
    cp /etc/sudoers.backup.* /etc/sudoers
    exit 1
fi

# 10. Финальная проверка
echo "=== ФИНАЛЬНАЯ ПРОВЕРКА ==="
echo "Созданные группы:"
getent group default_users
getent group secret_users

echo ""
echo "Созданные пользователи:"
echo "Пользователь user:"
id user
echo "Пользователи secret_users:"
for username in "${SECRET_USERS[@]}"; do
    id "$username"
done

echo ""
echo "Права домашних директорий:"
ls -la /home/

echo ""
echo "Права /var:"
ls -ld /var

echo ""
echo "=== ЗАВЕРШЕНО ==="
echo "Настройка пользователей и групп завершена успешно!"
echo ""
echo "Краткая сводка:"
echo "- Пользователь 'user' в группе 'default_users' (sudo без пароля)"
echo "- Пользователи 'secret_agent', 'secret_spy', 'secret_boss' в группе 'secret_users'"
echo "- Секретные пользователи могут читать домашние директории друг друга"
echo "- Каталог /var доступен всем пользователям"
echo "- Apache2 установлен и запущен"
