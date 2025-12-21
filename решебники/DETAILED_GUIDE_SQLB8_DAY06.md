# 📚 Подробное пошаговое руководство по SQLB8 (Day 06)

## 🎯 ВВЕДЕНИЕ

Это руководство поможет тебе самостоятельно выполнить все задания проекта SQLB8 (Day 06). Каждое задание разбито на маленькие шаги с подробными объяснениями.

---

## 📝 ЗАДАНИЕ 00: Discounts, discounts, everyone loves discounts

### 🎯 Цель задания
Создать новую таблицу `person_discounts` для хранения персональных скидок клиентов в разных пиццериях.

### 📖 Что нужно понять перед началом

**Концепция:** 
- Каждый клиент может иметь свою скидку в каждой пиццерии
- Например: Анна имеет скидку 10% в Pizza Hut, но 15% в Dominos
- Это связь "многие-ко-многим" между клиентами и пиццериями

**Структура таблицы:**
- `id` - уникальный идентификатор записи (Primary Key)
- `person_id` - ссылка на клиента (Foreign Key → person.id)
- `pizzeria_id` - ссылка на пиццерию (Foreign Key → pizzeria.id)
- `discount` - размер скидки в процентах (может быть дробным числом)

### 🔍 ШАГ 1: Изучи существующие таблицы

**Что делать:**
1. Открой файл `materials/model.sql`
2. Найди определение таблицы `person` и посмотри тип данных для `id`
3. Найди определение таблицы `pizzeria` и посмотри тип данных для `id`

**Что ты увидишь:**
```sql
create table person
( id bigint primary key , ... )

create table pizzeria
(id bigint primary key , ... )
```

**Вывод:** Тип данных для `id` - это `bigint`

### 🔍 ШАГ 2: Изучи примеры внешних ключей

**Что делать:**
Посмотри, как созданы внешние ключи в существующих таблицах:

```sql
-- Пример из таблицы menu:
constraint fk_menu_pizzeria_id foreign key (pizzeria_id) references pizzeria(id)

-- Пример из таблицы person_visits:
constraint fk_person_visits_person_id foreign key (person_id) references person(id)
```

**Что важно понять:**
- Имя ограничения: `fk_{имя_таблицы}_{имя_столбца}`
- Тип данных внешнего ключа должен совпадать с типом данных первичного ключа родительской таблицы

### 🔍 ШАГ 3: Определи тип данных для discount

**Что нужно:**
- Скидка может быть дробным числом (например, 10.5%)
- В PostgreSQL для дробных чисел используется тип `numeric`
- Можно указать точность: `numeric(5,2)` (5 цифр всего, 2 после запятой)
- Или просто `numeric` (без ограничений)

**Рекомендация:** Используй `numeric` без указания точности для гибкости

### ✍️ ШАГ 4: Напиши CREATE TABLE

**Алгоритм написания:**

1. **Начни с базовой структуры:**
```sql
CREATE TABLE person_discounts (
    -- здесь будут столбцы
);
```

2. **Добавь столбец id:**
```sql
CREATE TABLE person_discounts (
    id bigint PRIMARY KEY
);
```
   - `bigint` - потому что в других таблицах id имеет тип bigint
   - `PRIMARY KEY` - это первичный ключ

3. **Добавь столбец person_id:**
```sql
CREATE TABLE person_discounts (
    id bigint PRIMARY KEY,
    person_id bigint NOT NULL
);
```
   - `bigint` - потому что person.id имеет тип bigint
   - `NOT NULL` - клиент должен быть указан (обязательное поле)

4. **Добавь внешний ключ для person_id:**
```sql
CREATE TABLE person_discounts (
    id bigint PRIMARY KEY,
    person_id bigint NOT NULL,
    CONSTRAINT fk_person_discounts_person_id 
        FOREIGN KEY (person_id) REFERENCES person(id)
);
```
   - `CONSTRAINT fk_person_discounts_person_id` - имя ограничения по шаблону
   - `FOREIGN KEY (person_id)` - указываем столбец
   - `REFERENCES person(id)` - ссылаемся на person.id

5. **Добавь столбец pizzeria_id:**
```sql
CREATE TABLE person_discounts (
    id bigint PRIMARY KEY,
    person_id bigint NOT NULL,
    CONSTRAINT fk_person_discounts_person_id 
        FOREIGN KEY (person_id) REFERENCES person(id),
    pizzeria_id bigint NOT NULL
);
```

6. **Добавь внешний ключ для pizzeria_id:**
```sql
CREATE TABLE person_discounts (
    id bigint PRIMARY KEY,
    person_id bigint NOT NULL,
    CONSTRAINT fk_person_discounts_person_id 
        FOREIGN KEY (person_id) REFERENCES person(id),
    pizzeria_id bigint NOT NULL,
    CONSTRAINT fk_person_discounts_pizzeria_id 
        FOREIGN KEY (pizzeria_id) REFERENCES pizzeria(id)
);
```

7. **Добавь столбец discount:**
```sql
CREATE TABLE person_discounts (
    id bigint PRIMARY KEY,
    person_id bigint NOT NULL,
    CONSTRAINT fk_person_discounts_person_id 
        FOREIGN KEY (person_id) REFERENCES person(id),
    pizzeria_id bigint NOT NULL,
    CONSTRAINT fk_person_discounts_pizzeria_id 
        FOREIGN KEY (pizzeria_id) REFERENCES pizzeria(id),
    discount numeric
);
```
   - `numeric` - для дробных чисел (10.5, 22.0, 30.5 и т.д.)

### ✅ ШАГ 5: Проверь результат

**Что делать:**
1. Создай файл `src/ex00/day06_ex00.sql`
2. Вставь туда свой CREATE TABLE
3. Выполни скрипт в базе данных
4. Проверь, что таблица создана:
```sql
\d person_discounts
```

**Ожидаемый результат:**
Должна быть таблица с 4 столбцами: id, person_id, pizzeria_id, discount

---

## 📝 ЗАДАНИЕ 01: Let's set personal discounts

### 🎯 Цель задания
Заполнить таблицу `person_discounts` данными на основе истории заказов из таблицы `person_order`.

### 📖 Что нужно понять

**Логика расчета скидки:**
- Смотрим, сколько раз клиент заказывал в конкретной пиццерии
- Если 1 заказ → скидка 10.5%
- Если 2 заказа → скидка 22%
- Если 3+ заказов → скидка 30%

**Пример:**
- Клиент с id=1 заказал пиццу из пиццерии с id=1 один раз → скидка 10.5%
- Клиент с id=2 заказал пиццу из пиццерии с id=2 два раза → скидка 22%

### 🔍 ШАГ 1: Пойми структуру данных

**Что делать:**
1. Посмотри на таблицу `person_order`:
   - `person_id` - кто заказал
   - `menu_id` - что заказал
   - `order_date` - когда заказал

2. Пойми связь:
   - `menu.menu_id` → `menu.id`
   - `menu.pizzeria_id` → какая пиццерия

**Важно:** Нам нужно узнать, в какой пиццерии был заказ, поэтому нужен JOIN с таблицей `menu`

### 🔍 ШАГ 2: Напиши запрос для получения данных

**Алгоритм:**

1. **Начни с базового SELECT:**
```sql
SELECT 
    person_id,
    menu_id
FROM person_order;
```

2. **Добавь JOIN с menu, чтобы узнать pizzeria_id:**
```sql
SELECT 
    po.person_id,
    m.pizzeria_id
FROM person_order po
JOIN menu m ON po.menu_id = m.id;
```
   - `po` - алиас для person_order
   - `m` - алиас для menu
   - `JOIN menu m ON po.menu_id = m.id` - соединяем заказы с меню

3. **Добавь GROUP BY для подсчета количества заказов:**
```sql
SELECT 
    po.person_id,
    m.pizzeria_id,
    COUNT(*) AS order_count
FROM person_order po
JOIN menu m ON po.menu_id = m.id
GROUP BY po.person_id, m.pizzeria_id;
```
   - `GROUP BY po.person_id, m.pizzeria_id` - группируем по клиенту и пиццерии
   - `COUNT(*)` - считаем количество заказов в каждой группе

### 🔍 ШАГ 3: Добавь расчет скидки

**Что нужно:**
Использовать CASE для условной логики:

```sql
SELECT 
    po.person_id,
    m.pizzeria_id,
    COUNT(*) AS order_count,
    CASE 
        WHEN COUNT(*) = 1 THEN 10.5
        WHEN COUNT(*) = 2 THEN 22
        ELSE 30
    END AS discount
FROM person_order po
JOIN menu m ON po.menu_id = m.id
GROUP BY po.person_id, m.pizzeria_id;
```

**Объяснение CASE:**
- `CASE` - начало условного выражения
- `WHEN COUNT(*) = 1 THEN 10.5` - если 1 заказ, то скидка 10.5
- `WHEN COUNT(*) = 2 THEN 22` - если 2 заказа, то скидка 22
- `ELSE 30` - во всех остальных случаях (3+) скидка 30
- `END` - конец условного выражения

### 🔍 ШАГ 4: Добавь генерацию id

**Что нужно:**
Использовать `ROW_NUMBER() OVER ()` для создания последовательных id:

```sql
SELECT 
    ROW_NUMBER() OVER () AS id,
    po.person_id,
    m.pizzeria_id,
    CASE 
        WHEN COUNT(*) = 1 THEN 10.5
        WHEN COUNT(*) = 2 THEN 22
        ELSE 30
    END AS discount
FROM person_order po
JOIN menu m ON po.menu_id = m.id
GROUP BY po.person_id, m.pizzeria_id;
```

**Объяснение ROW_NUMBER():**
- `ROW_NUMBER()` - функция, которая нумерует строки
- `OVER ()` - окно для всех строк (без разделения)
- Результат: 1, 2, 3, 4, ... для каждой строки

### ✍️ ШАГ 5: Напиши INSERT INTO ... SELECT

**Структура:**
```sql
INSERT INTO table_name (column1, column2, ...)
SELECT column1, column2, ...
FROM ...
```

**Твой запрос:**
```sql
INSERT INTO person_discounts (id, person_id, pizzeria_id, discount)
SELECT 
    ROW_NUMBER() OVER () AS id,
    po.person_id,
    m.pizzeria_id,
    CASE 
        WHEN COUNT(*) = 1 THEN 10.5
        WHEN COUNT(*) = 2 THEN 22
        ELSE 30
    END AS discount
FROM person_order po
JOIN menu m ON po.menu_id = m.id
GROUP BY po.person_id, m.pizzeria_id;
```

**Объяснение:**
- `INSERT INTO person_discounts (id, person_id, pizzeria_id, discount)` - указываем таблицу и столбцы
- `SELECT ...` - данные, которые вставляем
- Порядок столбцов в INSERT должен совпадать с порядком в SELECT

### ✅ ШАГ 6: Проверь результат

**Что делать:**
1. Создай файл `src/ex01/day06_ex01.sql`
2. Вставь туда свой INSERT
3. Выполни скрипт
4. Проверь данные:
```sql
SELECT * FROM person_discounts ORDER BY person_id, pizzeria_id;
```

**Ожидаемый результат:**
Должны быть записи для каждой пары (person_id, pizzeria_id) с правильными скидками

---

## 📝 ЗАДАНИЕ 02: Let's recalculate a history of orders

### 🎯 Цель задания
Написать запрос, который показывает:
- Имя клиента
- Название пиццы
- Оригинальную цену
- Цену со скидкой
- Название пиццерии

### 📖 Что нужно понять

**Логика расчета цены со скидкой:**
- Если цена = 800, скидка = 22%
- Цена со скидкой = 800 * (1 - 22/100) = 800 * 0.78 = 624

**Формула:** `price * (1 - discount / 100)`

### 🔍 ШАГ 1: Определи, какие таблицы нужны

**Нужны таблицы:**
1. `person` - для имени клиента
2. `person_order` - для связи клиента с заказом
3. `menu` - для названия пиццы, цены и пиццерии
4. `pizzeria` - для названия пиццерии
5. `person_discounts` - для размера скидки

### 🔍 ШАГ 2: Построй JOIN цепочку

**Алгоритм:**

1. **Начни с person_order (основная таблица):**
```sql
SELECT *
FROM person_order po;
```

2. **Добавь JOIN с person (чтобы получить имя):**
```sql
SELECT *
FROM person_order po
JOIN person p ON po.person_id = p.id;
```

3. **Добавь JOIN с menu (чтобы получить пиццу и цену):**
```sql
SELECT *
FROM person_order po
JOIN person p ON po.person_id = p.id
JOIN menu m ON po.menu_id = m.id;
```

4. **Добавь JOIN с pizzeria (чтобы получить название пиццерии):**
```sql
SELECT *
FROM person_order po
JOIN person p ON po.person_id = p.id
JOIN menu m ON po.menu_id = m.id
JOIN pizzeria pz ON m.pizzeria_id = pz.id;
```

5. **Добавь LEFT JOIN с person_discounts (скидка может быть не у всех):**
```sql
SELECT *
FROM person_order po
JOIN person p ON po.person_id = p.id
JOIN menu m ON po.menu_id = m.id
JOIN pizzeria pz ON m.pizzeria_id = pz.id
LEFT JOIN person_discounts pd 
    ON po.person_id = pd.person_id 
    AND m.pizzeria_id = pd.pizzeria_id;
```
   - `LEFT JOIN` - потому что скидка может быть не у всех
   - Условие: `po.person_id = pd.person_id AND m.pizzeria_id = pd.pizzeria_id`
   - Нужны ОБА условия: и клиент, и пиццерия должны совпадать

### 🔍 ШАГ 3: Выбери нужные столбцы

**Что нужно в SELECT:**
```sql
SELECT 
    p.name,
    m.pizza_name,
    m.price,
    -- здесь будет цена со скидкой
    pz.name AS pizzeria_name
FROM person_order po
JOIN person p ON po.person_id = p.id
JOIN menu m ON po.menu_id = m.id
JOIN pizzeria pz ON m.pizzeria_id = pz.id
LEFT JOIN person_discounts pd 
    ON po.person_id = pd.person_id 
    AND m.pizzeria_id = pd.pizzeria_id;
```

### 🔍 ШАГ 4: Добавь расчет цены со скидкой

**Формула:** `price * (1 - discount / 100)`

**Но нужно учесть:**
- Если скидки нет (pd.discount IS NULL), то цена не меняется
- Используй COALESCE или CASE

**Вариант 1 с COALESCE:**
```sql
SELECT 
    p.name,
    m.pizza_name,
    m.price,
    m.price * (1 - COALESCE(pd.discount, 0) / 100) AS discount_price,
    pz.name AS pizzeria_name
FROM person_order po
JOIN person p ON po.person_id = p.id
JOIN menu m ON po.menu_id = m.id
JOIN pizzeria pz ON m.pizzeria_id = pz.id
LEFT JOIN person_discounts pd 
    ON po.person_id = pd.person_id 
    AND m.pizzeria_id = pd.pizzeria_id;
```

**Объяснение COALESCE:**
- `COALESCE(pd.discount, 0)` - если pd.discount NULL, то используй 0
- Если скидки нет, то скидка = 0%, цена не меняется

**Вариант 2 с CASE (более понятный):**
```sql
SELECT 
    p.name,
    m.pizza_name,
    m.price,
    CASE 
        WHEN pd.discount IS NULL THEN m.price
        ELSE m.price * (1 - pd.discount / 100)
    END AS discount_price,
    pz.name AS pizzeria_name
FROM person_order po
JOIN person p ON po.person_id = p.id
JOIN menu m ON po.menu_id = m.id
JOIN pizzeria pz ON m.pizzeria_id = pz.id
LEFT JOIN person_discounts pd 
    ON po.person_id = pd.person_id 
    AND m.pizzeria_id = pd.pizzeria_id;
```

### 🔍 ШАГ 5: Добавь сортировку

**Требование:** Сортировать по имени клиента и названию пиццы

```sql
SELECT 
    p.name,
    m.pizza_name,
    m.price,
    m.price * (1 - COALESCE(pd.discount, 0) / 100) AS discount_price,
    pz.name AS pizzeria_name
FROM person_order po
JOIN person p ON po.person_id = p.id
JOIN menu m ON po.menu_id = m.id
JOIN pizzeria pz ON m.pizzeria_id = pz.id
LEFT JOIN person_discounts pd 
    ON po.person_id = pd.person_id 
    AND m.pizzeria_id = pd.pizzeria_id
ORDER BY p.name, m.pizza_name;
```

**Объяснение ORDER BY:**
- `ORDER BY p.name` - сначала по имени клиента
- `, m.pizza_name` - потом по названию пиццы

### ✅ ШАГ 6: Проверь результат

**Что делать:**
1. Создай файл `src/ex02/day06_ex02.sql`
2. Вставь туда свой SELECT
3. Выполни запрос
4. Проверь, что:
   - Все столбцы присутствуют
   - discount_price меньше или равен price
   - Результаты отсортированы правильно

---

## 📝 ЗАДАНИЕ 03: Improvements are in a way

### 🎯 Цель задания
Создать уникальный многоколоночный индекс, который предотвратит дублирование пар (person_id, pizzeria_id).

### 📖 Что нужно понять

**Зачем нужен уникальный индекс:**
- Один клиент не может иметь две записи скидки в одной пиццерии
- Например: нельзя иметь две записи (person_id=1, pizzeria_id=1)
- Это обеспечивает целостность данных

**Что такое многоколоночный индекс:**
- Индекс на несколько столбцов сразу
- Порядок столбцов важен!

### 🔍 ШАГ 1: Пойми синтаксис CREATE UNIQUE INDEX

**Базовая структура:**
```sql
CREATE UNIQUE INDEX index_name 
ON table_name (column1, column2);
```

**Для нашего случая:**
```sql
CREATE UNIQUE INDEX idx_person_discounts_unique 
ON person_discounts (person_id, pizzeria_id);
```

**Объяснение:**
- `CREATE UNIQUE INDEX` - создаем уникальный индекс
- `idx_person_discounts_unique` - имя индекса (как указано в задании)
- `ON person_discounts` - на какой таблице
- `(person_id, pizzeria_id)` - на какие столбцы

### ✍️ ШАГ 2: Напиши CREATE INDEX

**Твой запрос:**
```sql
CREATE UNIQUE INDEX idx_person_discounts_unique 
ON person_discounts (person_id, pizzeria_id);
```

### 🔍 ШАГ 3: Напиши запрос для проверки индекса

**Что нужно:**
- Простой SELECT с WHERE по индексированным столбцам
- Использовать EXPLAIN ANALYZE для проверки

**Пример запроса:**
```sql
SET enable_seqscan = OFF;

EXPLAIN ANALYZE
SELECT person_id, pizzeria_id, discount
FROM person_discounts
WHERE person_id = 1 AND pizzeria_id = 1;
```

**Объяснение:**
- `SET enable_seqscan = OFF` - отключаем последовательное сканирование (чтобы заставить использовать индекс)
- `EXPLAIN ANALYZE` - показывает план выполнения
- `WHERE person_id = 1 AND pizzeria_id = 1` - условие по индексированным столбцам

**Что должно быть в выводе:**
```
Index Scan using idx_person_discounts_unique on person_discounts
```

### ✅ ШАГ 4: Проверь результат

**Что делать:**
1. Создай файл `src/ex03/day06_ex03.sql`
2. Вставь туда CREATE INDEX и EXPLAIN ANALYZE
3. Выполни скрипт
4. Проверь, что в выводе есть `Index Scan using idx_person_discounts_unique`

**Дополнительная проверка:**
Попробуй вставить дубликат (должна быть ошибка):
```sql
INSERT INTO person_discounts (id, person_id, pizzeria_id, discount)
VALUES (999, 1, 1, 10.5);
-- Должна быть ошибка, если запись с person_id=1 и pizzeria_id=1 уже есть
```

---

## 📝 ЗАДАНИЕ 04: We need more Data Consistency

### 🎯 Цель задания
Добавить ограничения (constraints) для обеспечения целостности данных в таблице `person_discounts`.

### 📖 Что нужно понять

**Типы ограничений:**
1. **NOT NULL** - столбец не может быть NULL
2. **DEFAULT** - значение по умолчанию
3. **CHECK** - проверка диапазона значений

### 🔍 ШАГ 1: Добавь ограничение NOT NULL для person_id

**Синтаксис:**
```sql
ALTER TABLE table_name 
ALTER COLUMN column_name SET NOT NULL;
```

**Для person_id:**
```sql
ALTER TABLE person_discounts 
ALTER COLUMN person_id SET NOT NULL;
```

**Но нужно добавить имя ограничения!**

**Правильный способ:**
```sql
ALTER TABLE person_discounts 
ADD CONSTRAINT ch_nn_person_id 
CHECK (person_id IS NOT NULL);
```

**Или можно использовать:**
```sql
ALTER TABLE person_discounts 
ALTER COLUMN person_id SET NOT NULL;

-- Но имя ограничения создастся автоматически
-- Чтобы задать имя, нужно удалить и создать заново:
ALTER TABLE person_discounts 
DROP CONSTRAINT person_discounts_person_id_not_null;

ALTER TABLE person_discounts 
ADD CONSTRAINT ch_nn_person_id 
CHECK (person_id IS NOT NULL);
```

**Самый простой способ (если столбец уже NOT NULL):**
```sql
-- Если столбец уже NOT NULL, просто добавь CHECK с именем:
ALTER TABLE person_discounts 
ADD CONSTRAINT ch_nn_person_id 
CHECK (person_id IS NOT NULL);
```

### 🔍 ШАГ 2: Добавь ограничение NOT NULL для pizzeria_id

```sql
ALTER TABLE person_discounts 
ADD CONSTRAINT ch_nn_pizzeria_id 
CHECK (pizzeria_id IS NOT NULL);
```

### 🔍 ШАГ 3: Добавь ограничение NOT NULL для discount

```sql
ALTER TABLE person_discounts 
ADD CONSTRAINT ch_nn_discount 
CHECK (discount IS NOT NULL);
```

### 🔍 ШАГ 4: Добавь значение по умолчанию для discount

**Синтаксис:**
```sql
ALTER TABLE table_name 
ALTER COLUMN column_name SET DEFAULT value;
```

**Для discount:**
```sql
ALTER TABLE person_discounts 
ALTER COLUMN discount SET DEFAULT 0;
```

**Объяснение:**
- Если при INSERT не указать discount, будет использоваться 0

### 🔍 ШАГ 5: Добавь ограничение диапазона для discount

**Синтаксис CHECK:**
```sql
ALTER TABLE table_name 
ADD CONSTRAINT constraint_name 
CHECK (condition);
```

**Для discount (от 0 до 100):**
```sql
ALTER TABLE person_discounts 
ADD CONSTRAINT ch_range_discount 
CHECK (discount >= 0 AND discount <= 100);
```

**Объяснение:**
- `discount >= 0` - скидка не может быть отрицательной
- `AND discount <= 100` - скидка не может быть больше 100%

### ✍️ ШАГ 6: Напиши все ALTER TABLE команды

**Полный скрипт:**
```sql
-- NOT NULL для person_id
ALTER TABLE person_discounts 
ADD CONSTRAINT ch_nn_person_id 
CHECK (person_id IS NOT NULL);

-- NOT NULL для pizzeria_id
ALTER TABLE person_discounts 
ADD CONSTRAINT ch_nn_pizzeria_id 
CHECK (pizzeria_id IS NOT NULL);

-- NOT NULL для discount
ALTER TABLE person_discounts 
ADD CONSTRAINT ch_nn_discount 
CHECK (discount IS NOT NULL);

-- DEFAULT для discount
ALTER TABLE person_discounts 
ALTER COLUMN discount SET DEFAULT 0;

-- Диапазон для discount (0-100)
ALTER TABLE person_discounts 
ADD CONSTRAINT ch_range_discount 
CHECK (discount >= 0 AND discount <= 100);
```

### ✅ ШАГ 7: Проверь результат

**Что делать:**
1. Создай файл `src/ex04/day06_ex04.sql`
2. Вставь туда все ALTER TABLE команды
3. Выполни скрипт
4. Проверь ограничения:
```sql
SELECT constraint_name, constraint_type 
FROM information_schema.table_constraints 
WHERE table_name = 'person_discounts';
```

**Дополнительная проверка:**
Попробуй вставить невалидные данные (должны быть ошибки):
```sql
-- Должна быть ошибка (discount > 100):
INSERT INTO person_discounts (id, person_id, pizzeria_id, discount)
VALUES (999, 1, 1, 150);

-- Должна быть ошибка (discount < 0):
INSERT INTO person_discounts (id, person_id, pizzeria_id, discount)
VALUES (999, 1, 1, -10);
```

---

## 📝 ЗАДАНИЕ 05: Data Governance Rules

### 🎯 Цель задания
Добавить комментарии к таблице и всем ее столбцам для документирования бизнес-назначения.

### 📖 Что нужно понять

**Зачем нужны комментарии:**
- Документирование структуры базы данных
- Объяснение бизнес-логики
- Помощь другим разработчикам понять назначение таблицы

**Синтаксис:**
```sql
COMMENT ON TABLE table_name IS 'описание';
COMMENT ON COLUMN table_name.column_name IS 'описание';
```

### 🔍 ШАГ 1: Напиши комментарий для таблицы

**Что писать:**
- Объясни, зачем нужна таблица
- Какая бизнес-логика

**Пример:**
```sql
COMMENT ON TABLE person_discounts IS 
'Таблица для хранения персональных скидок клиентов в различных пиццериях. Каждая запись представляет уникальную комбинацию клиента и пиццерии с соответствующим размером скидки в процентах.';
```

### 🔍 ШАГ 2: Напиши комментарии для каждого столбца

**id:**
```sql
COMMENT ON COLUMN person_discounts.id IS 
'Уникальный идентификатор записи о персональной скидке. Первичный ключ таблицы.';
```

**person_id:**
```sql
COMMENT ON COLUMN person_discounts.person_id IS 
'Идентификатор клиента, которому предоставлена скидка. Внешний ключ на таблицу person.';
```

**pizzeria_id:**
```sql
COMMENT ON COLUMN person_discounts.pizzeria_id IS 
'Идентификатор пиццерии, в которой действует скидка. Внешний ключ на таблицу pizzeria.';
```

**discount:**
```sql
COMMENT ON COLUMN person_discounts.discount IS 
'Размер персональной скидки в процентах. Может быть дробным числом. Диапазон значений: от 0 до 100.';
```

### ✍️ ШАГ 3: Напиши все COMMENT команды

**Полный скрипт:**
```sql
-- Комментарий для таблицы
COMMENT ON TABLE person_discounts IS 
'Таблица для хранения персональных скидок клиентов в различных пиццериях. Каждая запись представляет уникальную комбинацию клиента и пиццерии с соответствующим размером скидки в процентах.';

-- Комментарий для столбца id
COMMENT ON COLUMN person_discounts.id IS 
'Уникальный идентификатор записи о персональной скидке. Первичный ключ таблицы.';

-- Комментарий для столбца person_id
COMMENT ON COLUMN person_discounts.person_id IS 
'Идентификатор клиента, которому предоставлена скидка. Внешний ключ на таблицу person.';

-- Комментарий для столбца pizzeria_id
COMMENT ON COLUMN person_discounts.pizzeria_id IS 
'Идентификатор пиццерии, в которой действует скидка. Внешний ключ на таблицу pizzeria.';

-- Комментарий для столбца discount
COMMENT ON COLUMN person_discounts.discount IS 
'Размер персональной скидки в процентах. Может быть дробным числом. Диапазон значений: от 0 до 100.';
```

**Примечание:** Ты можешь написать комментарии на русском или английском языке - на твое усмотрение.

### ✅ ШАГ 4: Проверь результат

**Что делать:**
1. Создай файл `src/ex05/day06_ex05.sql`
2. Вставь туда все COMMENT команды
3. Выполни скрипт
4. Проверь комментарии:
```sql
SELECT 
    obj_description('person_discounts'::regclass, 'pg_class') AS table_comment;

SELECT 
    col_description('person_discounts'::regclass, ordinal_position) AS column_comment,
    column_name
FROM information_schema.columns
WHERE table_name = 'person_discounts'
ORDER BY ordinal_position;
```

---

## 📝 ЗАДАНИЕ 06: Let's automate Primary Key generation

### 🎯 Цель задания
Создать последовательность (sequence) для автоматической генерации id при вставке новых записей.

### 📖 Что нужно понять

**Что такое последовательность:**
- Объект базы данных, который генерирует последовательные числа
- Используется для автоматической генерации первичных ключей

**Зачем это нужно:**
- Не нужно вручную указывать id при INSERT
- Гарантирует уникальность
- Упрощает вставку данных

### 🔍 ШАГ 1: Создай последовательность

**Синтаксис:**
```sql
CREATE SEQUENCE sequence_name
START WITH start_value
INCREMENT BY increment_value;
```

**Для нашего случая:**
```sql
CREATE SEQUENCE seq_person_discounts
START WITH 1
INCREMENT BY 1;
```

**Объяснение:**
- `CREATE SEQUENCE` - создаем последовательность
- `seq_person_discounts` - имя последовательности (как указано в задании)
- `START WITH 1` - начинаем с 1
- `INCREMENT BY 1` - увеличиваем на 1 каждый раз

### 🔍 ШАГ 2: Установи значение по умолчанию для столбца id

**Синтаксис:**
```sql
ALTER TABLE table_name 
ALTER COLUMN column_name SET DEFAULT nextval('sequence_name');
```

**Для нашего случая:**
```sql
ALTER TABLE person_discounts 
ALTER COLUMN id SET DEFAULT nextval('seq_person_discounts');
```

**Объяснение:**
- `nextval('seq_person_discounts')` - функция, которая берет следующее значение из последовательности
- При каждом INSERT, если id не указан, будет использоваться следующее значение из последовательности

### 🔍 ШАГ 3: Установи текущее значение последовательности

**Проблема:**
- В таблице уже есть данные с id = 1, 2, 3, ...
- Если последовательность начнет с 1, будет конфликт!

**Решение:**
Нужно установить текущее значение последовательности = (количество строк в таблице) + 1

**Синтаксис:**
```sql
SELECT setval('sequence_name', value);
```

**Но как узнать количество строк?**
```sql
SELECT COUNT(*) FROM person_discounts;
```

**Как установить значение:**
```sql
SELECT setval('seq_person_discounts', (SELECT COUNT(*) FROM person_discounts) + 1);
```

**Объяснение:**
- `(SELECT COUNT(*) FROM person_discounts)` - подсчитываем количество строк
- `+ 1` - добавляем 1, чтобы следующее значение было уникальным
- `setval()` - устанавливает текущее значение последовательности

**Важно:** Запрещено использовать hard-coded значения! Нужно использовать формулу.

### ✍️ ШАГ 4: Напиши полный скрипт

**Полный скрипт:**
```sql
-- Создаем последовательность
CREATE SEQUENCE seq_person_discounts
START WITH 1
INCREMENT BY 1;

-- Устанавливаем значение по умолчанию для id
ALTER TABLE person_discounts 
ALTER COLUMN id SET DEFAULT nextval('seq_person_discounts');

-- Устанавливаем текущее значение последовательности
SELECT setval('seq_person_discounts', (SELECT COUNT(*) FROM person_discounts) + 1);
```

### ✅ ШАГ 5: Проверь результат

**Что делать:**
1. Создай файл `src/ex06/day06_ex06.sql`
2. Вставь туда все команды
3. Выполни скрипт
4. Проверь, что последовательность создана:
```sql
SELECT * FROM pg_sequences WHERE sequencename = 'seq_person_discounts';
```

5. Проверь, что значение по умолчанию установлено:
```sql
SELECT column_default 
FROM information_schema.columns 
WHERE table_name = 'person_discounts' AND column_name = 'id';
```

6. Проверь работу (вставь запись без указания id):
```sql
INSERT INTO person_discounts (person_id, pizzeria_id, discount)
VALUES (1, 1, 10.5);

-- Проверь, что id был сгенерирован автоматически:
SELECT * FROM person_discounts ORDER BY id DESC LIMIT 1;
```

---

## 🎯 ИТОГОВАЯ ШПАРГАЛКА

### Основные SQL-конструкции, которые ты использовал:

1. **CREATE TABLE:**
```sql
CREATE TABLE table_name (
    column1 type1,
    column2 type2,
    CONSTRAINT constraint_name FOREIGN KEY (column) REFERENCES other_table(id)
);
```

2. **INSERT INTO ... SELECT:**
```sql
INSERT INTO table_name (col1, col2, ...)
SELECT col1, col2, ...
FROM ...
GROUP BY ...
```

3. **JOIN:**
```sql
SELECT ...
FROM table1 t1
JOIN table2 t2 ON t1.id = t2.foreign_id
LEFT JOIN table3 t3 ON ...
```

4. **CASE:**
```sql
CASE 
    WHEN condition1 THEN value1
    WHEN condition2 THEN value2
    ELSE value3
END
```

5. **GROUP BY и агрегатные функции:**
```sql
SELECT col1, COUNT(*), SUM(col2)
FROM table
GROUP BY col1;
```

6. **CREATE INDEX:**
```sql
CREATE UNIQUE INDEX index_name 
ON table_name (col1, col2);
```

7. **ALTER TABLE:**
```sql
ALTER TABLE table_name 
ADD CONSTRAINT constraint_name CHECK (condition);

ALTER TABLE table_name 
ALTER COLUMN column_name SET DEFAULT value;
```

8. **COMMENT:**
```sql
COMMENT ON TABLE table_name IS 'описание';
COMMENT ON COLUMN table_name.column_name IS 'описание';
```

9. **CREATE SEQUENCE:**
```sql
CREATE SEQUENCE sequence_name
START WITH 1
INCREMENT BY 1;

ALTER TABLE table_name 
ALTER COLUMN id SET DEFAULT nextval('sequence_name');

SELECT setval('sequence_name', value);
```

---

## 🚀 Удачи в выполнении проекта!

Помни:
- Читай задания внимательно
- Проверяй синтаксис
- Тестируй свои запросы
- Не бойся экспериментировать
- Используй EXPLAIN ANALYZE для оптимизации

