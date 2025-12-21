## Подробное пошаговое руководство по SQLB9 (Day 07)

Это руководство — “супер-подробная” версия по проекту **SQLB9 (Day 07)** из репозитория `SQLB9_OLAP`. Оно сделано в стиле твоего гайда для SQLB8 Day06, но:

- больше теории (что именно делает `GROUP BY`, чем `WHERE` отличается от `HAVING`, почему бывает “взрыв строк” в `JOIN`, как устроены `UNION/UNION ALL`, нюансы `COUNT`, `AVG`, `ROUND`, явные касты и деление целых);
- больше пошаговых алгоритмов (сборка запроса по слоям);
- больше типовых ошибок и способов самопроверки.

Важно: проект в Школе 21 — про понимание. Не копируй слепо. Даже если берёшь шаблон из гайда — перепроверь, почему он работает, и попробуй объяснить себе вслух.

---

## 0) Быстрый контекст: про что Day 07

Day 07 — это базовая аналитика на операционных данных (OLTP) через агрегации (OLAP-подход):

- “Сырые события”: визиты (`person_visits`) и заказы (`person_order`) — это факты/транзакции.
- “Справочники”: люди (`person`), рестораны (`pizzeria`), меню (`menu`) — это измерения (dimension tables).

Агрегация превращает “много строк событий” в “маленькую таблицу метрик”, удобную для анализа.

---

## 1) Подготовка проекта (один раз перед упражнениями)

### 1.1. Структура репозитория и куда класть решения

По `README_RUS.md` для каждого задания ты создаёшь файл строго по шаблону:

- `src/ex00/day07_ex00.sql`
- `src/ex01/day07_ex01.sql`
- …
- `src/ex09/day07_ex09.sql`

### 1.2. Инициализация базы данными

В `materials/model.sql` описана вся схема и вставлены тестовые данные. Его нужно применить к твоей базе PostgreSQL.

**Что внутри (самое важное для Day07):**

- `person(id bigint, name, age integer, gender, address)`
- `pizzeria(id bigint, name, rating numeric)`
- `menu(id bigint, pizzeria_id bigint, pizza_name, price numeric)`
- `person_visits(id bigint, person_id bigint, pizzeria_id bigint, visit_date date)`
- `person_order(id bigint, person_id bigint, menu_id bigint, order_date date)`

**Главные связи:**

- `person_visits.person_id -> person.id`
- `person_visits.pizzeria_id -> pizzeria.id`
- `menu.pizzeria_id -> pizzeria.id`
- `person_order.person_id -> person.id`
- `person_order.menu_id -> menu.id`

### 1.3. Мини-шпаргалка по агрегатам и группировке

- **`COUNT(*)`**: считает строки в группе.
- **`COUNT(column)`**: считает только НЕ-NULL значения.
- **`COUNT(DISTINCT x)`**: считает уникальные значения `x`.
- **`AVG(x)`**: среднее. Если `x` — integer, PostgreSQL вернёт `numeric`. Но важно следить за делением целых в выражениях.
- **`ROUND(numeric, digits)`**: округление `numeric` до `digits` знаков.
- **`GROUP BY`**: объединяет строки в группы, и на каждой группе можно считать агрегаты.
- **`WHERE`**: фильтрует строки ДО группировки.
- **`HAVING`**: фильтрует группы ПОСЛЕ группировки.
- **Правило `GROUP BY`**: в `SELECT` либо агрегат, либо поле из `GROUP BY`.

---

## 2) Упражнение 00 — Simple aggregated information

### 2.1. Цель

Вернуть `person_id` и **количество визитов** этого человека в любые пиццерии.

Отсортировать:

- по `count_of_visits` по убыванию,
- затем по `person_id` по возрастанию.

### 2.2. Теория: что именно мы считаем

Таблица `person_visits` — это “факт визита”. Каждая строка — один визит (уникальность закреплена ограничением по `(person_id, pizzeria_id, visit_date)`).

Значит количество визитов человека = количество строк в `person_visits` с этим `person_id`.

### 2.3. Сборка запроса по шагам

#### Шаг 1: минимальный набросок

- **База**: `FROM person_visits`
- **Группировка**: по `person_id`
- **Агрегат**: `COUNT(*)`

```sql
SELECT
  person_id,
  COUNT(*) AS count_of_visits
FROM person_visits
GROUP BY person_id;
```

#### Шаг 2: сортировка по требованиям

```sql
SELECT
  person_id,
  COUNT(*) AS count_of_visits
FROM person_visits
GROUP BY person_id
ORDER BY
  count_of_visits DESC,
  person_id ASC;
```

### 2.4. Частые ошибки

- **Использовать `COUNT(person_id)` вместо `COUNT(*)`**: тут это не сломает (поле NOT NULL), но это плохая привычка.
- **Сортировать по `COUNT(*)` напрямую**: можно, но лучше сортировать по алиасу `count_of_visits` — читабельнее.

### 2.5. Чек-лист самопроверки

- Возвращаются ровно 2 столбца: `person_id`, `count_of_visits`.
- Нет `JOIN` (он здесь не нужен).
- Сортировка соответствует требованию.

Файл: `src/ex00/day07_ex00.sql`

---

## 3) Упражнение 01 — Let’s see real names

### 3.1. Цель

Модифицировать решение из ex00:

- вместо `person_id` вернуть `person.name`;
- дополнительно показать **только топ-4 людей** с максимальным числом визитов;
- итоговый результат **отсортировать по имени**.

### 3.2. Теория: “топ-N” и сортировки “в два этапа”

Типичный паттерн:

1) сначала считаем метрику и выбираем **топ-N** по метрике,
2) затем упорядочиваем уже выбранные строки “как красиво показать”.

Если попытаться сделать `ORDER BY name` + `LIMIT 4` в одном уровне, ты получишь “первые 4 по алфавиту”, а не “топ-4 по визитам”.

### 3.3. Сборка запроса по шагам

#### Шаг 1: агрегируем визиты по людям (как в ex00)

```sql
SELECT
  person_id,
  COUNT(*) AS count_of_visits
FROM person_visits
GROUP BY person_id;
```

#### Шаг 2: присоединяем `person`, чтобы получить `name`

Главная идея:

- агрегат делаем по `person_id`,
- затем `JOIN person p ON p.id = pv.person_id`.

```sql
SELECT
  p.name,
  COUNT(*) AS count_of_visits
FROM person_visits pv
JOIN person p ON p.id = pv.person_id
GROUP BY p.name;
```

Замечание: в этих данных имя уникально, но в реальной жизни нет. Более универсально группировать по `p.id, p.name`. Однако упражнение просит только `name`, и автопроверка может ожидать именно так. Если сомневаешься — сделай `GROUP BY p.id, p.name`, а в `SELECT` выводи `p.name`.

#### Шаг 3: выбрать топ-4 по визитам

Тут нужна сортировка по `count_of_visits DESC`, а затем `LIMIT 4`. Но после этого нужно отсортировать по `name`.

Значит делаем подзапрос:

```sql
SELECT
  name,
  count_of_visits
FROM (
  SELECT
    p.name AS name,
    COUNT(*) AS count_of_visits
  FROM person_visits pv
  JOIN person p ON p.id = pv.person_id
  GROUP BY p.name
  ORDER BY count_of_visits DESC, name ASC
  LIMIT 4
) AS top4
ORDER BY name ASC;
```

Почему внутри тоже полезно сортировать по `name` вторым ключом?

- если у нескольких людей одинаковое число визитов, детерминированность результата повышается.

### 3.4. Частые ошибки

- **`LIMIT 4` после сортировки по имени** — выбираешь не топ, а “по алфавиту”.
- **Забыть внешний `ORDER BY name`** — в результате будет сортировка по визитам.

### 3.5. Чек-лист самопроверки

- Вывод: `name`, `count_of_visits`.
- Ровно 4 строки (если данных хватает).
- Итог отсортирован по `name`.

Файл: `src/ex01/day07_ex01.sql`

---

## 4) Упражнение 02 — Restaurants statistics

### 4.1. Цель

Показать **топ-3 пиццерии**:

- по количеству заказов,
- и отдельно по количеству визитов,

и объединить их в один список, добавив столбец `action_type` со значениями:

- `'order'` для заказов,
- `'visit'` для визитов.

Отсортировать финальный результат:

- по `action_type` по возрастанию,
- затем по `count` по убыванию.

### 4.2. Теория: почему тут нужен `UNION ALL`

Нам нужно “склеить два независимых топа” в одну выдачу.

- **`UNION`** удаляет дубликаты (делает `DISTINCT` поверх результата) — это лишняя работа и может поменять результат.
- **`UNION ALL`** просто конкатенирует результаты — именно то, что нужно.

### 4.3. Сборка запроса по шагам

#### Шаг 1: топ-3 по заказам

Чтобы посчитать заказы по пиццерии, нужно пройти цепочку:

`person_order -> menu -> pizzeria`

Потому что в `person_order` есть `menu_id`, а `pizzeria_id` хранится в `menu`.

```sql
SELECT
  pz.name,
  COUNT(*) AS count,
  'order' AS action_type
FROM person_order po
JOIN menu m ON m.id = po.menu_id
JOIN pizzeria pz ON pz.id = m.pizzeria_id
GROUP BY pz.name
ORDER BY count DESC, pz.name ASC
LIMIT 3;
```

#### Шаг 2: топ-3 по визитам

Для визитов всё проще:

`person_visits -> pizzeria`

```sql
SELECT
  pz.name,
  COUNT(*) AS count,
  'visit' AS action_type
FROM person_visits pv
JOIN pizzeria pz ON pz.id = pv.pizzeria_id
GROUP BY pz.name
ORDER BY count DESC, pz.name ASC
LIMIT 3;
```

#### Шаг 3: объединение + финальная сортировка

Проблема: если сделать `ORDER BY` внутри каждого запроса и потом `UNION ALL`, то **финальная сортировка может потеряться** (SQL не гарантирует порядок без внешнего `ORDER BY`).

Значит делаем:

- `UNION ALL`,
- затем `ORDER BY` снаружи.

```sql
(
  SELECT
    pz.name,
    COUNT(*) AS count,
    'order' AS action_type
  FROM person_order po
  JOIN menu m ON m.id = po.menu_id
  JOIN pizzeria pz ON pz.id = m.pizzeria_id
  GROUP BY pz.name
  ORDER BY count DESC, pz.name ASC
  LIMIT 3
)
UNION ALL
(
  SELECT
    pz.name,
    COUNT(*) AS count,
    'visit' AS action_type
  FROM person_visits pv
  JOIN pizzeria pz ON pz.id = pv.pizzeria_id
  GROUP BY pz.name
  ORDER BY count DESC, pz.name ASC
  LIMIT 3
)
ORDER BY action_type ASC, count DESC;
```

### 4.4. Частые ошибки

- **Не ставить `LIMIT 3` внутри каждой части**: тогда ты ограничишь итоговую “склейку”, а не каждый топ.
- **Использовать `UNION` вместо `UNION ALL`**: можно случайно потерять строки.

### 4.5. Чек-лист самопроверки

- 3 строки с `action_type='order'` и 3 строки с `action_type='visit'`.
- Имена ресторанов берутся из `pizzeria.name`.
- Итоговая сортировка именно снаружи.

Файл: `src/ex02/day07_ex02.sql`

---

## 5) Упражнение 03 — Restaurants statistics #2

### 5.1. Цель

На основе логики из ex02:

- собрать статистику по визитам и заказам по каждому ресторану,
- объединить по названию пиццерии,
- посчитать `total_count = orders + visits`, учитывая, что ресторан может присутствовать только в одной из таблиц,
- отсортировать по `total_count DESC`, затем по `name ASC`.

### 5.2. Теория: почему здесь нужен `FULL OUTER JOIN` и `COALESCE`

Если ресторан есть только в заказах, но нет визитов — `INNER JOIN` его выкинет.

Чтобы сохранить все рестораны из обеих статистик, нужен:

- **`FULL OUTER JOIN`**: оставляет строки и слева, и справа.

Но после `FULL OUTER JOIN` будут `NULL` в недостающей части, поэтому:

- **`COALESCE(x, 0)`** превращает `NULL` в 0 для корректной суммы.

### 5.3. Сборка запроса по шагам

#### Шаг 1: подготовить агрегаты отдельно

Заказы:

```sql
SELECT
  pz.name,
  COUNT(*) AS order_count
FROM person_order po
JOIN menu m ON m.id = po.menu_id
JOIN pizzeria pz ON pz.id = m.pizzeria_id
GROUP BY pz.name;
```

Визиты:

```sql
SELECT
  pz.name,
  COUNT(*) AS visit_count
FROM person_visits pv
JOIN pizzeria pz ON pz.id = pv.pizzeria_id
GROUP BY pz.name;
```

#### Шаг 2: объединить и посчитать total

```sql
WITH
orders AS (
  SELECT
    pz.name,
    COUNT(*) AS order_count
  FROM person_order po
  JOIN menu m ON m.id = po.menu_id
  JOIN pizzeria pz ON pz.id = m.pizzeria_id
  GROUP BY pz.name
),
visits AS (
  SELECT
    pz.name,
    COUNT(*) AS visit_count
  FROM person_visits pv
  JOIN pizzeria pz ON pz.id = pv.pizzeria_id
  GROUP BY pz.name
)
SELECT
  COALESCE(o.name, v.name) AS name,
  COALESCE(o.order_count, 0) + COALESCE(v.visit_count, 0) AS total_count
FROM orders o
FULL OUTER JOIN visits v ON v.name = o.name
ORDER BY total_count DESC, name ASC;
```

### 5.4. Частые ошибки

- **Использовать `LEFT JOIN`** и потерять рестораны, которые есть только в правой таблице.
- **Забыть `COALESCE`**: `NULL + число = NULL`, сумма “сломается”.

### 5.5. Чек-лист самопроверки

- В выдаче нет `NULL` в `name`.
- `total_count` — целое число.
- Сортировка по требованиям.

Файл: `src/ex03/day07_ex03.sql`

---

## 6) Упражнение 04 — Clause for groups (WHERE запрещён)

### 6.1. Цель

Вывести:

- имя человека,
- количество визитов

только для тех людей, у кого визитов **строго больше 3**.

**Запрещено:** использовать `WHERE`.

### 6.2. Теория: почему `HAVING`, а не `WHERE`

Условие “визитов больше 3” относится к **агрегату** `COUNT(*)`.

- `WHERE` применяется до группировки, а агрегатов “ещё нет”.
- `HAVING` применяется после группировки, когда агрегаты уже посчитаны.

### 6.3. Сборка запроса по шагам

```sql
SELECT
  p.name,
  COUNT(*) AS count_of_visits
FROM person_visits pv
JOIN person p ON p.id = pv.person_id
GROUP BY p.name
HAVING COUNT(*) > 3;
```

Сортировка в задании не обязательна, но можно сделать “красиво”:

```sql
ORDER BY p.name;
```

### 6.4. Частые ошибки

- **Попытаться написать `WHERE COUNT(*) > 3`** — это синтаксическая ошибка.
- **Фильтровать по `visit_date` через `HAVING`** — это можно, но тогда ты фильтруешь по агрегату; даты лучше фильтровать `WHERE` (но здесь `WHERE` запрещён).

### 6.5. Чек-лист самопроверки

- В запросе нет слова `WHERE`.
- Фильтр на “> 3” стоит в `HAVING`.

Файл: `src/ex04/day07_ex04.sql`

---

## 7) Упражнение 05 — Person’s uniqueness (GROUP BY запрещён)

### 7.1. Цель

Вывести список **уникальных имён людей**, которые сделали **хотя бы один заказ**.

**Запрещено:**

- `GROUP BY`,
- любые операции над множествами (например, `UNION` и т.п.).

### 7.2. Теория: как получить уникальность без `GROUP BY`

Твой инструмент тут — **`DISTINCT`**.

Логика:

- `person_order` содержит `person_id`,
- присоединяем `person` чтобы взять `name`,
- делаем `SELECT DISTINCT p.name`.

### 7.3. Запрос

```sql
SELECT DISTINCT
  p.name
FROM person_order po
JOIN person p ON p.id = po.person_id
ORDER BY p.name;
```

### 7.4. Частые ошибки

- **Использовать `GROUP BY p.name`** — нарушает запрет.
- **Использовать `UNION` для склейки** (например, с другой таблицей) — нарушает запрет на set operations.

### 7.5. Чек-лист самопроверки

- Нет `GROUP BY`.
- Нет `UNION/INTERSECT/EXCEPT`.
- В выдаче один столбец `name`, отсортирован.

Файл: `src/ex05/day07_ex05.sql`

---

## 8) Упражнение 06 — Restaurant metrics

### 8.1. Цель

Для каждой пиццерии вернуть:

- количество заказов,
- среднюю цену,
- максимальную цену,
- минимальную цену

по проданным пиццам.

Отсортировать по названию пиццерии. Среднюю цену округлить до 2 знаков.

### 8.2. Теория: почему агрегации делаются по меню/заказам

“Проданные пиццы” — это строки `person_order`.

Цена пиццы хранится в `menu.price`.

Значит цепочка:

`pizzeria -> menu -> person_order`

и агрегаты считаются по строкам заказов (каждая строка = продажа одной позиции).

### 8.3. Запрос (пошагово)

#### Шаг 1: собрать базовый набор строк “заказ + цена + пиццерия”

```sql
SELECT
  pz.name,
  m.price
FROM person_order po
JOIN menu m ON m.id = po.menu_id
JOIN pizzeria pz ON pz.id = m.pizzeria_id;
```

#### Шаг 2: добавить агрегаты

```sql
SELECT
  pz.name,
  COUNT(*) AS count_of_orders,
  ROUND(AVG(m.price), 2) AS average_price,
  MAX(m.price) AS max_price,
  MIN(m.price) AS min_price
FROM person_order po
JOIN menu m ON m.id = po.menu_id
JOIN pizzeria pz ON pz.id = m.pizzeria_id
GROUP BY pz.name
ORDER BY pz.name;
```

Примечание по типам:

- `menu.price` — `numeric`, значит `AVG(m.price)` тоже будет `numeric`.
- `ROUND(numeric, 2)` валиден.

### 8.4. Частые ошибки

- **Пытаться аггрегировать без `JOIN menu`** — тогда цены негде взять.
- **Округлять через `ROUND(AVG(price::float),2)`** — можно, но float даёт погрешности; на `numeric` надёжнее.

### 8.5. Чек-лист самопроверки

- В выдаче ровно 5 столбцов как в задании.
- `average_price` с 2 знаками после запятой (как минимум математически).
- Сортировка по `pz.name`.

Файл: `src/ex06/day07_ex06.sql`

---

## 9) Упражнение 07 — Average global rating

### 9.1. Цель

Вернуть общий средний рейтинг всех пиццерий в столбце `global_rating`, округлить до 4 знаков.

### 9.2. Теория: агрегат без GROUP BY

Если `GROUP BY` нет, то агрегат считается “по всей таблице” и результат — одна строка.

### 9.3. Запрос

```sql
SELECT
  ROUND(AVG(rating), 4) AS global_rating
FROM pizzeria;
```

### 9.4. Частые ошибки

- **Добавить `GROUP BY id` или `name`** — получишь несколько строк, а нужно одну.

Файл: `src/ex07/day07_ex07.sql`

---

## 10) Упражнение 08 — Find pizzeria’s restaurant locations

### 10.1. Цель

Считаем, что человек посещает пиццерии только в своём городе (город = `person.address`).

Нужно вывести:

1) `address` (город/адрес),
2) `pizzeria.name`,
3) число заказов жителей этого адреса в этой пиццерии.

Отсортировать по адресу, затем по названию пиццерии.

### 10.2. Теория: группировка по “измерениям”

Здесь “измерения”:

- адрес (`person.address`)
- ресторан (`pizzeria.name`)

Метрика:

- количество заказов (`COUNT(*)` по строкам `person_order`)

### 10.3. Сборка запроса по шагам

#### Шаг 1: собрать строки “заказ + адрес + пиццерия”

```sql
SELECT
  p.address,
  pz.name AS pizzeria_name
FROM person_order po
JOIN person p ON p.id = po.person_id
JOIN menu m ON m.id = po.menu_id
JOIN pizzeria pz ON pz.id = m.pizzeria_id;
```

#### Шаг 2: агрегировать

```sql
SELECT
  p.address,
  pz.name,
  COUNT(*) AS count_of_orders
FROM person_order po
JOIN person p ON p.id = po.person_id
JOIN menu m ON m.id = po.menu_id
JOIN pizzeria pz ON pz.id = m.pizzeria_id
GROUP BY p.address, pz.name
ORDER BY p.address, pz.name;
```

### 10.4. Частые ошибки

- **Группировать только по адресу** — потеряешь разрез по пиццерии.
- **Забыть `JOIN menu`** — не узнаешь, какая пиццерия у заказа.

Файл: `src/ex08/day07_ex08.sql`

---

## 11) Упражнение 09 — Explicit type transformation

### 11.1. Цель

По каждому адресу (`person.address`) вернуть:

- `formula`: значение выражения  
  **максимальный возраст − (минимальный возраст / максимальный возраст)**
- `average`: средний возраст по адресу
- `comparison`: результат сравнения `formula > average` (true/false)

Отсортировать по адресу.

Ключевая часть упражнения — “явное преобразование типов”.

### 11.2. Теория: проблема “деления целых” и зачем нужен `CAST`

В PostgreSQL:

- `age` — `integer`.
- `MIN(age)` и `MAX(age)` — тоже `integer`.

Если написать:

```sql
MIN(age) / MAX(age)
```

то это будет **целочисленное деление** (например, `13/45 = 0`), что сломает формулу.

Нужно превратить хотя бы один операнд в `numeric`, чтобы деление стало дробным:

- `MIN(age)::numeric / MAX(age)`
- или `CAST(MIN(age) AS numeric) / MAX(age)`

### 11.3. Сборка запроса по шагам

#### Шаг 1: агрегаты по адресу

```sql
SELECT
  address,
  MIN(age) AS min_age,
  MAX(age) AS max_age,
  AVG(age) AS avg_age
FROM person
GROUP BY address;
```

#### Шаг 2: вычислить формулу с явным кастом

```sql
SELECT
  address,
  (MAX(age) - (MIN(age)::numeric / MAX(age))) AS formula,
  AVG(age) AS average
FROM person
GROUP BY address;
```

#### Шаг 3: добавить сравнение и округление (чтобы результат был стабильным)

В примере из задания числа выглядят как с 2 знаками. Часто автопроверка ожидает именно так.

Паттерн: посчитать “сырые” значения в CTE, затем округлить и сравнить уже округлённые.

```sql
WITH agg AS (
  SELECT
    address,
    (MAX(age) - (MIN(age)::numeric / MAX(age))) AS formula_raw,
    AVG(age)::numeric AS average_raw
  FROM person
  GROUP BY address
)
SELECT
  address,
  ROUND(formula_raw, 2) AS formula,
  ROUND(average_raw, 2) AS average,
  (ROUND(formula_raw, 2) > ROUND(average_raw, 2)) AS comparison
FROM agg
ORDER BY address;
```

Почему тут `AVG(age)::numeric`?

- `AVG(integer)` и так вернёт `numeric`, но явный каст подчёркивает смысл упражнения.

### 11.4. Частые ошибки

- **Забыть каст и получить целочисленное деление** → формула станет почти равной `MAX(age)`.
- **Сравнивать “сырое” и “округлённое”** → можно попасть на пограничные эффекты.
- **Пытаться использовать алиасы `formula` и `average` прямо в `SELECT` для `comparison`**: в PostgreSQL алиас в той же проекции не всегда доступен; безопаснее вынести в CTE/подзапрос.

Файл: `src/ex09/day07_ex09.sql`

---

## 12) Мини-набор советов “как проверять себя быстро”

- **Сверяй количество строк**:
  - агрегации по человеку ≈ число людей, которые присутствуют в фактах;
  - агрегации по адресу ≈ число уникальных адресов.
- **Проверяй “взрыв строк” при JOIN**:
  - если случайно джойнишь “факт к факту” без ключа, `COUNT(*)` станет слишком большим.
- **Включай здравый смысл**:
  - `MIN(price)` не может быть больше `MAX(price)`;
  - `average_price` должен быть между min и max;
  - в ex07 должен быть ровно 1 ряд.
- **Если что-то странно — выведи промежуточную таблицу** (как мы делали в шагах): это лучший способ отладки SQL.

---

## 13) Итого

Ты закрыл ключевые аналитические паттерны:

- агрегации и `GROUP BY`,
- топ-N через “двухэтапную” сортировку,
- `UNION ALL` для склейки витрин,
- `FULL OUTER JOIN` + `COALESCE` для “неполных” данных,
- фильтрация групп через `HAVING`,
- уникальность через `DISTINCT` при запрете `GROUP BY`,
- вычисляемые метрики и явные касты типов.


