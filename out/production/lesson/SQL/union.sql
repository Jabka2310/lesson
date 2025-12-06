-- Задача 1 (UNION):
-- У нас есть список людей (person) и список пиццерий
-- (pizzeria).
-- Выведи в один столбец названия всех пиццерий и
-- имена всех женщин из таблицы person.
-- Колонку назови object_name.
-- Отсортируй результат по алфавиту.
-- Подумай, нужен здесь UNION или UNION ALL?
-- Задача 2 (INTERSECT):
-- Найди ID людей, которые посещали пиццерии
-- (person_visits) И при этом заказывали пиццу
-- (person_order) в один и тот же день.
-- Подсказка: Тебе нужно найти пересечение по двум
-- колонкам сразу: person_id и date (в таблицах 
-- это visit_date и order_date).
-- Задача 3 (EXCEPT):
-- Найди список пицц (названий), которые есть
-- в меню (menu), но которые никто никогда 
-- не заказывал (person_order).
-- Сначала получи список всех названий из menu.
-- Вычти из него список названий, которые 
-- фигурируют в заказах (тебе придется 
-- сделать подзапрос или JOIN внутри второго 
-- SELECT, чтобы добраться от person_order 
-- до menu.pizza_name).
-- Задача 4 (Комбинированная):
-- Найди ID всех людей, которые посетили 
-- пиццерию 'Dominos' (id=2), но НЕ посетили 
-- пиццерию 'Pizza Hut' (id=1).
-- Подсказка: SELECT ... WHERE pizzeria_id=2 
-- EXCEPT SELECT ... WHERE pizzeria_id=1.
-- Готов решать? Пиши, если нужны подсказки


-- lesson 1

SELECT name AS object_name
FROM pizzeria 
UNION
SELECT name AS object_name
FROM person 
WHERE gender = 'female'
ORDER BY object_name;


--lesson 2

SELECT person_id, visit_date
FROM person_visits
INTERSECT
SELECT person_id, order_date
FROM person_order;


--lesson 3
