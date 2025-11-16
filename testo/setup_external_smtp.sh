#!/bin/bash

# Скрипт для настройки отправки через внешний SMTP
# Требует запуск от имени root

# Проверка на запуск от root
if [[ $EUID -ne 0 ]]; then
   echo "Этот скрипт должен быть запущен от имени root!"
   exit 1
fi

echo "Настройка отправки через внешний SMTP сервер..."

# Выбор провайдера
echo "Выберите ваш email провайдер:"
echo "1) Gmail"
echo "2) Yandex"
echo "3) Mail.ru"
echo "4) Другой (ручная настройка)"

read -p "Введите номер (1-4): " PROVIDER_CHOICE

case $PROVIDER_CHOICE in
    1)
        SMTP_SERVER="smtp.gmail.com"
        SMTP_PORT="587"
        PROVIDER_NAME="Gmail"
        ;;
    2)
        SMTP_SERVER="smtp.yandex.ru"
        SMTP_PORT="587"
        PROVIDER_NAME="Yandex"
        ;;
    3)
        SMTP_SERVER="smtp.mail.ru"
        SMTP_PORT="587"
        PROVIDER_NAME="Mail.ru"
        ;;
    4)
        read -p "Введите SMTP сервер: " SMTP_SERVER
        read -p "Введите SMTP порт: " SMTP_PORT
        PROVIDER_NAME="Custom"
        ;;
    *)
        echo "Неверный выбор!"
        exit 1
        ;;
esac

# Запрос учетных данных
read -p "Введите ваш email адрес: " EMAIL_ADDRESS
read -s -p "Введите пароль приложения (не обычный пароль!): " EMAIL_PASSWORD
echo ""

if [[ -z "$EMAIL_ADDRESS" || -z "$EMAIL_PASSWORD" ]]; then
    echo "Email и пароль не могут быть пустыми!"
    exit 1
fi

echo "Настраиваем $PROVIDER_NAME..."

# Создаем резервную копию конфигурации
cp /etc/postfix/main.cf /etc/postfix/main.cf.backup.$(date +%Y%m%d_%H%M%S)

# Создаем файл с паролем
cat > /etc/postfix/sasl_passwd << EOF
[$SMTP_SERVER]:$SMTP_PORT $EMAIL_ADDRESS:$EMAIL_PASSWORD
EOF

# Устанавливаем правильные права на файл с паролем
chmod 600 /etc/postfix/sasl_passwd

# Создаем хеш-файл
postmap /etc/postfix/sasl_passwd

# Обновляем конфигурацию main.cf
cat >> /etc/postfix/main.cf << EOF

# Настройки для внешнего SMTP ($PROVIDER_NAME)
relayhost = [$SMTP_SERVER]:$SMTP_PORT
smtp_sasl_auth_enable = yes
smtp_sasl_password_maps = hash:/etc/postfix/sasl_passwd
smtp_sasl_security_options = noanonymous
smtp_tls_security_level = encrypt
smtp_tls_CAfile = /etc/ssl/certs/ca-certificates.crt
EOF

# Перезапускаем Postfix
if command -v systemctl &> /dev/null; then
    systemctl restart postfix
    echo "Postfix перезапущен"
elif command -v service &> /dev/null; then
    service postfix restart
    echo "Postfix перезапущен"
fi

# Тестовая отправка
echo "Отправляем тестовое письмо через $PROVIDER_NAME..."

cat > /tmp/test_external_smtp.txt << EOF
Это тестовое письмо через внешний SMTP ($PROVIDER_NAME).

Время отправки: $(date)
Хост: $(hostname -f)
SMTP сервер: $SMTP_SERVER:$SMTP_PORT

Если вы получили это письмо, значит настройка внешнего SMTP прошла успешно!

С уважением,
Система мониторинга логов
EOF

mail -s "Тест внешнего SMTP - $PROVIDER_NAME ($(hostname))" "$EMAIL_ADDRESS" < /tmp/test_external_smtp.txt

# Очищаем временный файл
rm -f /tmp/test_external_smtp.txt

echo ""
echo "=== ЗАВЕРШЕНО ==="
echo "Настройка внешнего SMTP ($PROVIDER_NAME) завершена!"
echo ""
echo "Важные замечания:"
echo "- Используйте пароль приложения, а не обычный пароль"
echo "- Для Gmail: включите двухфакторную аутентификацию и создайте пароль приложения"
echo "- Для Yandex: включите двухфакторную аутентификацию и создайте пароль приложения"
echo "- Для Mail.ru: используйте пароль приложения"
echo ""
echo "Файлы конфигурации:"
echo "- /etc/postfix/main.cf (обновленная конфигурация)"
echo "- /etc/postfix/sasl_passwd (учетные данные)"
echo "- /etc/postfix/sasl_passwd.db (хеш-файл)"
