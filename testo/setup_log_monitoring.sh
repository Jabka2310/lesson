#!/bin/bash

# Скрипт для настройки автоматической отправки логов
# Требует запуск от имени root

# Проверка на запуск от root
if [[ $EUID -ne 0 ]]; then
   echo "Этот скрипт должен быть запущен от имени root!"
   exit 1
fi

echo "Начинаем настройку автоматической отправки логов..."

# Запрос email адреса
read -p "Введите ваш email адрес (например, user@gmail.com): " EMAIL_ADDRESS

if [[ -z "$EMAIL_ADDRESS" ]]; then
    echo "Email адрес не может быть пустым!"
    exit 1
fi

echo "Используем email: $EMAIL_ADDRESS"

# 1. Установка необходимых пакетов
echo "=== УСТАНОВКА НЕОБХОДИМЫХ ПАКЕТОВ ==="

if command -v apt-get &> /dev/null; then
    echo "Используем apt-get..."
    apt-get update
    apt-get install -y logwatch postfix mailutils
elif command -v yum &> /dev/null; then
    echo "Используем yum..."
    yum install -y logwatch postfix mailx
elif command -v dnf &> /dev/null; then
    echo "Используем dnf..."
    dnf install -y logwatch postfix mailx
elif command -v pacman &> /dev/null; then
    echo "Используем pacman..."
    pacman -S --noconfirm logwatch postfix mailx
else
    echo "Не удалось определить пакетный менеджер!"
    exit 1
fi

# 2. Настройка Postfix
echo "=== НАСТРОЙКА POSTFIX ==="

# Создаем резервную копию конфигурации
cp /etc/postfix/main.cf /etc/postfix/main.cf.backup.$(date +%Y%m%d_%H%M%S)

# Создаем новый конфигурационный файл main.cf
cat > /etc/postfix/main.cf << EOF
# Основные настройки Postfix
smtpd_banner = \$myhostname ESMTP \$mail_name (Ubuntu)
biff = no
append_dot_mydomain = no
readme_directory = no

# TLS параметры
smtpd_tls_cert_file=/etc/ssl/certs/ssl-cert-snakeoil.pem
smtpd_tls_key_file=/etc/ssl/private/ssl-cert-snakeoil.key
smtpd_use_tls=yes
smtpd_tls_session_cache_database = btree:\${data_directory}/smtpd_scache
smtp_tls_session_cache_database = btree:\${data_directory}/smtp_scache

# Настройки аутентификации
smtpd_sasl_auth_enable = yes
smtpd_sasl_security_options = noanonymous
smtpd_sasl_local_domain = \$myhostname

# Настройки сети
myhostname = \$(hostname -f)
alias_maps = hash:/etc/aliases
alias_database = hash:/etc/aliases
myorigin = /etc/mailname
mydestination = \$myhostname, localhost.\$mydomain, localhost, \$mydomain
relayhost = 
mynetworks = 127.0.0.0/8 [::ffff:127.0.0.0]/104 [::1]/128
mailbox_size_limit = 0
recipient_delimiter = +
inet_interfaces = all
inet_protocols = ipv4

# Настройки для отправки через внешний SMTP (Gmail/Yandex/Mail.ru)
# Раскомментируйте и настройте для вашего провайдера:

# Для Gmail:
# relayhost = [smtp.gmail.com]:587
# smtp_sasl_auth_enable = yes
# smtp_sasl_password_maps = hash:/etc/postfix/sasl_passwd
# smtp_sasl_security_options = noanonymous
# smtp_tls_security_level = encrypt
# smtp_tls_CAfile = /etc/ssl/certs/ca-certificates.crt

# Для Yandex:
# relayhost = [smtp.yandex.ru]:587
# smtp_sasl_auth_enable = yes
# smtp_sasl_password_maps = hash:/etc/postfix/sasl_passwd
# smtp_sasl_security_options = noanonymous
# smtp_tls_security_level = encrypt

# Для Mail.ru:
# relayhost = [smtp.mail.ru]:587
# smtp_sasl_auth_enable = yes
# smtp_sasl_password_maps = hash:/etc/postfix/sasl_passwd
# smtp_sasl_security_options = noanonymous
# smtp_tls_security_level = encrypt
EOF

echo "Конфигурация Postfix создана"

# 3. Настройка Logwatch
echo "=== НАСТРОЙКА LOGWATCH ==="

# Создаем резервную копию конфигурации logwatch
if [ -f /etc/logwatch/conf/logwatch.conf ]; then
    cp /etc/logwatch/conf/logwatch.conf /etc/logwatch/conf/logwatch.conf.backup.$(date +%Y%m%d_%H%M%S)
fi

# Создаем конфигурацию logwatch для детализированных отчетов
cat > /etc/logwatch/conf/logwatch.conf << EOF
# Конфигурация Logwatch для детализированных отчетов
LogDir = /var/log
TmpDir = /tmp
MailTo = $EMAIL_ADDRESS
MailFrom = logwatch@\$(hostname)
Range = yesterday
Detail = High
Service = All
Format = html
EOF

echo "Конфигурация Logwatch создана"

# 4. Создание скрипта для отправки логов
echo "=== СОЗДАНИЕ СКРИПТА ОТПРАВКИ ЛОГОВ ==="

cat > /usr/local/bin/send_daily_logs.sh << 'EOF'
#!/bin/bash

# Скрипт для отправки ежедневных логов
# Запускается через cron

EMAIL_ADDRESS="EMAIL_PLACEHOLDER"
HOSTNAME=$(hostname -f)
DATE=$(date -d "yesterday" +%Y-%m-%d)

# Создаем временный файл для логов
TEMP_LOG="/tmp/daily_log_report_$(date +%Y%m%d).html"

# Генерируем детализированный отчет logwatch
logwatch --detail High --range yesterday --format html --output file --filename "$TEMP_LOG"

# Проверяем, что файл создан и не пустой
if [ -f "$TEMP_LOG" ] && [ -s "$TEMP_LOG" ]; then
    # Отправляем отчет по email
    echo "Отправляем ежедневный отчет о логах за $DATE" | mail -s "Ежедневный отчет о логах - $HOSTNAME ($DATE)" -a "$TEMP_LOG" "$EMAIL_ADDRESS"
    
    # Очищаем временный файл
    rm -f "$TEMP_LOG"
    
    echo "$(date): Отчет о логах отправлен на $EMAIL_ADDRESS" >> /var/log/logwatch_sender.log
else
    echo "$(date): Ошибка - не удалось создать отчет о логах" >> /var/log/logwatch_sender.log
fi
EOF

# Заменяем placeholder на реальный email
sed -i "s/EMAIL_PLACEHOLDER/$EMAIL_ADDRESS/g" /usr/local/bin/send_daily_logs.sh

# Делаем скрипт исполняемым
chmod +x /usr/local/bin/send_daily_logs.sh

echo "Скрипт отправки логов создан: /usr/local/bin/send_daily_logs.sh"

# 5. Настройка Crontab
echo "=== НАСТРОЙКА CRONTAB ==="

# Создаем файл cron.txt с конфигурацией
cat > cron.txt << EOF
# Ежедневная отправка логов в 00:00
0 0 * * * /usr/local/bin/send_daily_logs.sh

# Альтернативные варианты (раскомментируйте при необходимости):
# 0 0 * * * logwatch --detail High --range yesterday --mailto $EMAIL_ADDRESS --format html
# 0 0 * * * /usr/sbin/logwatch --detail High --range yesterday --mailto $EMAIL_ADDRESS --format html --service All
EOF

echo "Файл cron.txt создан с конфигурацией планировщика"

# Добавляем задачу в crontab root
(crontab -l 2>/dev/null; echo "0 0 * * * /usr/local/bin/send_daily_logs.sh") | crontab -

echo "Задача добавлена в crontab root"

# 6. Запуск и настройка Postfix
echo "=== ЗАПУСК POSTFIX ==="

# Перезапускаем Postfix
if command -v systemctl &> /dev/null; then
    systemctl restart postfix
    systemctl enable postfix
    echo "Postfix перезапущен и включен автозапуск"
elif command -v service &> /dev/null; then
    service postfix restart
    echo "Postfix перезапущен"
fi

# 7. Тестовая отправка
echo "=== ТЕСТОВАЯ ОТПРАВКА ==="
echo "Отправляем тестовое письмо..."

# Создаем тестовое сообщение
cat > /tmp/test_email.txt << EOF
Это тестовое письмо от системы мониторинга логов.

Время отправки: $(date)
Хост: $(hostname -f)
IP адрес: $(hostname -I | awk '{print $1}')

Если вы получили это письмо, значит настройка прошла успешно!
Ежедневные отчеты о логах будут приходить каждый день в 00:00.

С уважением,
Система мониторинга логов
EOF

# Отправляем тестовое письмо
mail -s "Тест настройки мониторинга логов - $(hostname)" "$EMAIL_ADDRESS" < /tmp/test_email.txt

# Очищаем временный файл
rm -f /tmp/test_email.txt

echo "Тестовое письмо отправлено на $EMAIL_ADDRESS"

# 8. Финальная проверка
echo "=== ФИНАЛЬНАЯ ПРОВЕРКА ==="

echo "Проверяем статус Postfix:"
if command -v systemctl &> /dev/null; then
    systemctl status postfix --no-pager -l
else
    service postfix status
fi

echo ""
echo "Проверяем crontab:"
crontab -l

echo ""
echo "Проверяем конфигурацию Logwatch:"
cat /etc/logwatch/conf/logwatch.conf

echo ""
echo "=== ЗАВЕРШЕНО ==="
echo "Настройка автоматической отправки логов завершена!"
echo ""
echo "Краткая сводка:"
echo "- Postfix настроен и запущен"
echo "- Logwatch настроен для детализированных отчетов"
echo "- Crontab настроен на отправку логов каждый день в 00:00"
echo "- Тестовое письмо отправлено на $EMAIL_ADDRESS"
echo ""
echo "Файлы конфигурации:"
echo "- /etc/postfix/main.cf (конфигурация Postfix)"
echo "- cron.txt (конфигурация планировщика)"
echo "- /usr/local/bin/send_daily_logs.sh (скрипт отправки)"
echo ""
echo "Для настройки отправки через внешний SMTP (Gmail/Yandex/Mail.ru):"
echo "1. Отредактируйте /etc/postfix/main.cf"
echo "2. Раскомментируйте соответствующие строки"
echo "3. Создайте файл /etc/postfix/sasl_passwd с учетными данными"
echo "4. Перезапустите Postfix"
