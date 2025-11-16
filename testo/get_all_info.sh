#!/bin/bash

# Скрипт для сбора системной информации
# Требует запуск от имени root

# Проверка на запуск от root
if [[ $EUID -ne 0 ]]; then
   echo "Этот скрипт должен быть запущен от имени root!"
   exit 1
fi

echo "Начинаем сбор системной информации..."

# Создаем временный файл для информации
INFO_FILE="info"

# Очищаем файл если он существует
> "$INFO_FILE"

echo "=== СБОР СИСТЕМНОЙ ИНФОРМАЦИИ ===" | tee -a "$INFO_FILE"
echo "Дата и время: $(date)" | tee -a "$INFO_FILE"
echo "" | tee -a "$INFO_FILE"

# 1. Установленные пакеты
echo "=== УСТАНОВЛЕННЫЕ ПАКЕТЫ ===" | tee -a "$INFO_FILE"
echo "Список установленных пакетов:" | tee -a "$INFO_FILE"

# Определяем тип пакетного менеджера
if command -v dpkg &> /dev/null; then
    echo "Используется dpkg (Debian/Ubuntu)" | tee -a "$INFO_FILE"
    dpkg -l | tee -a "$INFO_FILE"
elif command -v rpm &> /dev/null; then
    echo "Используется rpm (Red Hat/CentOS/Fedora)" | tee -a "$INFO_FILE"
    rpm -qa | tee -a "$INFO_FILE"
elif command -v pacman &> /dev/null; then
    echo "Используется pacman (Arch Linux)" | tee -a "$INFO_FILE"
    pacman -Q | tee -a "$INFO_FILE"
else
    echo "Не удалось определить пакетный менеджер" | tee -a "$INFO_FILE"
fi

echo "" | tee -a "$INFO_FILE"

# 2. Запущенные процессы
echo "=== ЗАПУЩЕННЫЕ ПРОЦЕССЫ ===" | tee -a "$INFO_FILE"
echo "Список запущенных процессов:" | tee -a "$INFO_FILE"
ps aux | tee -a "$INFO_FILE"

echo "" | tee -a "$INFO_FILE"

# 3. Открытые порты
echo "=== ОТКРЫТЫЕ ПОРТЫ ===" | tee -a "$INFO_FILE"
echo "Список открытых портов:" | tee -a "$INFO_FILE"

# Проверяем доступность netstat
if command -v netstat &> /dev/null; then
    netstat -tuln | tee -a "$INFO_FILE"
elif command -v ss &> /dev/null; then
    ss -tuln | tee -a "$INFO_FILE"
else
    echo "netstat и ss не найдены" | tee -a "$INFO_FILE"
fi

echo "" | tee -a "$INFO_FILE"

# 4. Установка пакетов cowsay и sl
echo "=== УСТАНОВКА ПАКЕТОВ ===" | tee -a "$INFO_FILE"
echo "Устанавливаем пакеты cowsay и sl..." | tee -a "$INFO_FILE"

# Определяем пакетный менеджер и устанавливаем пакеты
if command -v apt-get &> /dev/null; then
    echo "Используем apt-get для установки..." | tee -a "$INFO_FILE"
    apt-get update
    apt-get install -y cowsay sl
elif command -v yum &> /dev/null; then
    echo "Используем yum для установки..." | tee -a "$INFO_FILE"
    yum install -y cowsay sl
elif command -v dnf &> /dev/null; then
    echo "Используем dnf для установки..." | tee -a "$INFO_FILE"
    dnf install -y cowsay sl
elif command -v pacman &> /dev/null; then
    echo "Используем pacman для установки..." | tee -a "$INFO_FILE"
    pacman -S --noconfirm cowsay sl
else
    echo "Не удалось определить пакетный менеджер для установки" | tee -a "$INFO_FILE"
fi

echo "" | tee -a "$INFO_FILE"

# 5. Версия ядра и ОС
echo "=== ВЕРСИЯ ЯДРА И ОС ===" | tee -a "$INFO_FILE"
echo "Версия ядра: $(uname -r)" | tee -a "$INFO_FILE"
echo "Тип ОС: $(uname -s)" | tee -a "$INFO_FILE"
echo "Архитектура: $(uname -m)" | tee -a "$INFO_FILE"

# Дополнительная информация об ОС
if [ -f /etc/os-release ]; then
    echo "Информация об ОС:" | tee -a "$INFO_FILE"
    cat /etc/os-release | tee -a "$INFO_FILE"
elif [ -f /etc/redhat-release ]; then
    echo "Информация об ОС:" | tee -a "$INFO_FILE"
    cat /etc/redhat-release | tee -a "$INFO_FILE"
elif [ -f /etc/debian_version ]; then
    echo "Информация об ОС:" | tee -a "$INFO_FILE"
    echo "Debian версия: $(cat /etc/debian_version)" | tee -a "$INFO_FILE"
fi

echo "" | tee -a "$INFO_FILE"

# 6. Создание архива
echo "=== СОЗДАНИЕ АРХИВА ===" | tee -a "$INFO_FILE"
echo "Создаем архив OS_RESULT.tar..." | tee -a "$INFO_FILE"

# Удаляем старый архив если существует
if [ -f "OS_RESULT.tar" ]; then
    rm "OS_RESULT.tar"
fi

# Создаем новый архив
tar -cf OS_RESULT.tar "$INFO_FILE"

if [ $? -eq 0 ]; then
    echo "Архив OS_RESULT.tar успешно создан!" | tee -a "$INFO_FILE"
    echo "Размер архива: $(du -h OS_RESULT.tar | cut -f1)" | tee -a "$INFO_FILE"
else
    echo "Ошибка при создании архива!" | tee -a "$INFO_FILE"
fi

echo "" | tee -a "$INFO_FILE"
echo "=== ЗАВЕРШЕНО ===" | tee -a "$INFO_FILE"
echo "Сбор системной информации завершен!" | tee -a "$INFO_FILE"
echo "Результат сохранен в файле: $INFO_FILE" | tee -a "$INFO_FILE"
echo "Архив создан: OS_RESULT.tar" | tee -a "$INFO_FILE"

# Показываем содержимое архива
echo "Содержимое архива:" | tee -a "$INFO_FILE"
tar -tf OS_RESULT.tar | tee -a "$INFO_FILE"
