# Project 02 — Java Bootcamp: Pet Info

## Детальная инструкция с объяснением и поэтапным решением

---

## Содержание

1. [Общая информация о проекте](#общая-информация-о-проекте)
2. [Задание 1. Список питомцев](#задание-1-список-питомцев)
3. [Задание 2. Определение количества корма питомцу](#задание-2-определение-количества-корма-питомцу)
4. [Задание 3. Списки травоядных и всеядных питомцев](#задание-3-списки-травоядных-и-всеядных-питомцев)
5. [Задание 4. Увеличение возраста в функциональной парадигме](#задание-4-увеличение-возраста-в-функциональной-парадигме)
6. [Задание 5. Отслеживание прогулок питомцев](#задание-5-отслеживание-прогулок-питомцев)
7. [Задание 6. Итератор питомцев](#задание-6-итератор-питомцев)

---

## Общая информация о проекте

**Цель проекта:** научиться применять ООП, процедурный и мультипарадигмальный подход в Java, писать код в функциональной парадигме.

**Структура:** каждое задание — отдельный проект в директории `T02/src/exercise0`, `T02/src/exercise1`, ... `T02/src/exercise5`.

**Важно:**
- Работать в директории `src`.
- Делать push только в ветку `develop`.
- Если следующее задание опирается на предыдущее — скопировать предыдущий проект в новую папку и продолжить в нём.

**Общие требования к вводу:**
- При неподдерживаемом типе питомца: вывести `Incorrect input. Unsupported pet type` и перейти к следующему вводу.
- При возрасте ≤ 0: вывести `Incorrect input. Age <= 0` и перейти к следующему вводу.
- При ошибке парсинга числа: вывести `Could not parse a number. Please, try again` и повторить ввод (не завершать программу).

---

## Задание 1. Список питомцев

### Что нужно сделать

Модуль формирует список питомцев (Dog, Cat) и выводит информацию о каждом. Ввод: количество питомцев, затем для каждого — тип (dog/cat), имя, возраст.

### Ключевые концепции

- **Абстрактный класс** — базовый класс с общими полями и конструктором; от него наследуются Dog и Cat.
- **private-поля** — доступ только через геттеры.
- **Переопределение toString()** — единый формат вывода для каждого типа животного.
- **Обработка ошибок ввода** — проверка типа, возраста и парсинга чисел без падения программы.

### Пошаговое решение

#### Шаг 1. Абстрактный класс Animal

Класс хранит имя и возраст. Конструктор и геттеры — обязательны.

```java
public abstract class Animal {
    private final String name;
    private final int age;

    public Animal(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
```

**Почему `final` для полей:** имя и возраст задаются один раз при создании и не меняются в рамках этого задания — так проще избежать ошибок.

#### Шаг 2. Класс Dog

Наследуется от Animal, передаёт параметры в `super`, переопределяет `toString()`.

```java
public class Dog extends Animal {

    public Dog(String name, int age) {
        super(name, age);
    }

    @Override
    public String toString() {
        return "Dog name = " + getName() + ", age = " + getAge();
    }
}
```

Формат строки по заданию: `Dog name = [имя], age = [возраст]`.

#### Шаг 3. Класс Cat

Аналогично Dog, другой текст в `toString()`.

```java
public class Cat extends Animal {

    public Cat(String name, int age) {
        super(name, age);
    }

    @Override
    public String toString() {
        return "Cat name = " + getName() + ", age = " + getAge();
    }
}
```

#### Шаг 4. Ввод с проверками

Нужно:
1. Считать количество питомцев (с повторным вводом при ошибке парсинга).
2. В цикле для каждого питомца: тип → имя → возраст.
3. Проверять тип (только dog/cat), возраст (> 0).
4. При ошибке — вывести сообщение и **не** добавлять питомца, перейти к следующей итерации (или повторить ввод для числа).

Вспомогательный метод для безопасного чтения целого числа:

```java
private static int readInt(Scanner scanner) {
    while (true) {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Could not parse a number. Please, try again");
        }
    }
}
```

Логика ввода одного питомца (псевдокод):

- Считать строку типа (dog/cat).
- Если не dog и не cat → вывести `Incorrect input. Unsupported pet type`, continue.
- Считать имя.
- Считать возраст через `readInt`.
- Если возраст <= 0 → вывести `Incorrect input. Age <= 0`, continue.
- Создать `new Dog(name, age)` или `new Cat(name, age)` и добавить в список `pets`.

#### Шаг 5. Главный класс (Main) и вывод

Список: `List<Animal> pets = new ArrayList<>()`.

После цикла ввода вывести каждого питомца:

```java
for (Animal pet : pets) {
    System.out.println(pet);
}
```

Или через forEach:

```java
pets.forEach(System.out::println);
```

### Пример полной структуры Main (задание 1)

```java
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Animal> pets = new ArrayList<>();

        int count = readInt(scanner);

        for (int i = 0; i < count; i++) {
            String type = scanner.nextLine().trim().toLowerCase();
            if (!type.equals("dog") && !type.equals("cat")) {
                System.out.println("Incorrect input. Unsupported pet type");
                continue;
            }
            String name = scanner.nextLine().trim();
            int age = readInt(scanner);
            if (age <= 0) {
                System.out.println("Incorrect input. Age <= 0");
                continue;
            }
            if (type.equals("dog")) {
                pets.add(new Dog(name, age));
            } else {
                pets.add(new Cat(name, age));
            }
        }

        pets.forEach(System.out::println);
        scanner.close();
    }

    private static int readInt(Scanner scanner) {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Could not parse a number. Please, try again");
            }
        }
    }
}
```

### Частые ошибки

- Не обрабатывать `NumberFormatException` при парсинге числа — программа падает при вводе букв.
- Добавлять питомца в список при некорректном типе или возрасте — добавлять только после всех проверок.
- Путать порядок ввода: сначала тип, потом имя, потом возраст (как в примерах).

---

## Задание 2. Определение количества корма питомцу

### Что нужно сделать

Расширить модель: у каждого животного есть **имя, возраст и масса**. По типу питомца считать порцию корма (граммы) и выводить в формате задания, включая массу и feed.

### Изменения по сравнению с заданием 1

- В `Animal` добавляется поле **mass** (double) и геттер.
- В `Animal` объявляется абстрактный метод `double getFeedInfoKg()`.
- **Dog:** корм = масса × 0.3.
- **Cat:** корм = масса × 0.1.
- Дополнительная проверка: если масса ≤ 0 → вывести `Incorrect input. Mass <= 0` и не добавлять питомца.
- Формат вывода: `Dog name = ..., age = ..., mass = [2 знака после запятой], feed = [2 знака]`.

### Пошаговое решение

#### Шаг 1. Animal с полем mass и абстрактным методом

```java
public abstract class Animal {
    private final String name;
    private final int age;
    private final double mass;

    public Animal(String name, int age, double mass) {
        this.name = name;
        this.age = age;
        this.mass = mass;
    }

    public String getName() { return name; }
    public int getAge() { return age; }
    public double getMass() { return mass; }

    public abstract double getFeedInfoKg();
}
```

#### Шаг 2. Dog — формула корма и toString

```java
public class Dog extends Animal {

    public Dog(String name, int age, double mass) {
        super(name, age, mass);
    }

    @Override
    public double getFeedInfoKg() {
        return getMass() * 0.3;
    }

    @Override
    public String toString() {
        return String.format("Dog name = %s, age = %d, mass = %.2f, feed = %.2f",
                getName(), getAge(), getMass(), getFeedInfoKg());
    }
}
```

#### Шаг 3. Cat — формула корма и toString

```java
public class Cat extends Animal {

    public Cat(String name, int age, double mass) {
        super(name, age, mass);
    }

    @Override
    public double getFeedInfoKg() {
        return getMass() * 0.1;
    }

    @Override
    public String toString() {
        return String.format("Cat name = %s, age = %d, mass = %.2f, feed = %.2f",
                getName(), getAge(), getMass(), getFeedInfoKg());
    }
}
```

#### Шаг 4. Чтение вещественного числа

Аналогично `readInt` — метод, который в цикле парсит `Double.parseDouble(scanner.nextLine().trim())` и при `NumberFormatException` выводит `Could not parse a number. Please, try again`.

#### Шаг 5. Ввод и проверка массы

После считывания возраста считывать массу через этот метод. Если `mass <= 0` — вывести `Incorrect input. Mass <= 0` и не добавлять питомца. Иначе создавать `new Dog(name, age, mass)` или `new Cat(name, age, mass)`.

Вывод по заданию уже заложен в `toString()`.

### Важно

- Порядок ввода для каждого питомца: тип → имя → возраст → масса.
- Для вывода с двумя знаками после запятой удобно использовать `String.format("%.2f", value)`.

---

## Задание 3. Списки травоядных и всеядных питомцев

### Что нужно сделать

- Добавить типы питомцев: **dog, cat, hamster, guinea** (Guinea Pig).
- Разделить их на две группы: **травоядные (Herbivore)** и **всеядные (Omnivore)**.
- Сначала вывести всех травоядных (hamster, guinea), затем всех всеядных (dog, cat).
- У каждого класса свой метод интерфейса и свой текст в `toString()`.

### Ключевые концепции

- **Интерфейсы** Herbivore и Omnivore задают поведение без реализации.
- **Herbivore** — метод `chill()` возвращает строку.
- **Omnivore** — метод `hunt()` возвращает строку.
- Классы реализуют соответствующий интерфейс и вызывают свой метод в `toString()`.

### Пошаговое решение

#### Шаг 1. Интерфейсы

```java
public interface Herbivore {
    String chill();
}

public interface Omnivore {
    String hunt();
}
```

#### Шаг 2. Animal (без mass)

Как в задании 1: только name и age, конструктор и геттеры.

#### Шаг 3. Dog — Omnivore

```java
public class Dog extends Animal implements Omnivore {

    public Dog(String name, int age) {
        super(name, age);
    }

    @Override
    public String hunt() {
        return "I can hunt for robbers";
    }

    @Override
    public String toString() {
        return "Dog name = " + getName() + ", age = " + getAge() + ". " + hunt();
    }
}
```

#### Шаг 4. Cat — Omnivore

```java
public class Cat extends Animal implements Omnivore {

    public Cat(String name, int age) {
        super(name, age);
    }

    @Override
    public String hunt() {
        return "I can hunt for mice";
    }

    @Override
    public String toString() {
        return "Cat name = " + getName() + ", age = " + getAge() + ". " + hunt();
    }
}
```

#### Шаг 5. Hamster — Herbivore

```java
public class Hamster extends Animal implements Herbivore {

    public Hamster(String name, int age) {
        super(name, age);
    }

    @Override
    public String chill() {
        return "I can chill for 8 hours";
    }

    @Override
    public String toString() {
        return "Hamster name = " + getName() + ", age = " + getAge() + ". " + chill();
    }
}
```

#### Шаг 6. GuineaPig — Herbivore

```java
public class GuineaPig extends Animal implements Herbivore {

    public GuineaPig(String name, int age) {
        super(name, age);
    }

    @Override
    public String chill() {
        return "I can chill for 12 hours";
    }

    @Override
    public String toString() {
        return "GuineaPig name = " + getName() + ", age = " + getAge() + ". " + chill();
    }
}
```

#### Шаг 7. Ввод типа

Поддерживаемые строки после trim/toLowerCase: `dog`, `cat`, `hamster`, `guinea`. Иначе — `Incorrect input. Unsupported pet type`.

#### Шаг 8. Создание питомцев по типу

- dog → `new Dog(name, age)`
- cat → `new Cat(name, age)`
- hamster → `new Hamster(name, age)`
- guinea → `new GuineaPig(name, age)`

#### Шаг 9. Вывод: сначала травоядные, потом всеядные

Вариант 1 — два прохода по списку:

```java
// Сначала травоядные
pets.stream()
    .filter(pet -> pet instanceof Herbivore)
    .forEach(System.out::println);
// Потом всеядные
pets.stream()
    .filter(pet -> pet instanceof Omnivore)
    .forEach(System.out::println);
```

Вариант 2 — собрать два списка и вывести по очереди (так же сохраняется порядок добавления внутри каждой группы).

### Важно

- В примере вывода травоядные идут первыми (GuineaPig, Hamster), затем Dog, Cat — порядок вывода должен соответствовать.

---

## Задание 4. Увеличение возраста в функциональной парадигме

### Что нужно сделать

- Модель снова только Dog и Cat с полями name, age (как в задании 1).
- Для всех питомцев **старше 10 лет** увеличить возраст на 1.
- Сделать это в **функциональной парадигме**: только Stream API, **без циклов** (for, while и т.п.).

### Пошаговое решение

#### Шаг 1. Animal, Dog, Cat

Как в задании 1. Важно: в задании сказано «увеличить возраст». Поля в нашем Animal были `final`, поэтому либо:
- убрать `final` у `age` и добавить сеттер `setAge(int age)`,  
либо  
- заменить объект в списке на нового с возрастом age + 1.

Второй вариант лучше подходит под функциональный стиль (иммутабельность). Тогда в Animal нужен способ создать копию с новым возрастом. Проще всего для проверки — сделать поле age не final и добавить setAge, а «увеличение» выполнить через Stream.

Пример с изменяемым возрастом (минимальные изменения к заданию 1):

```java
public abstract class Animal {
    private final String name;
    private int age;  // не final

    public Animal(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() { return name; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
}
```

Тогда увеличить возраст только тем, кому > 10:

```java
pets.stream()
    .filter(pet -> pet.getAge() > 10)
    .forEach(pet -> pet.setAge(pet.getAge() + 1));
pets.forEach(System.out::println);
```

Так мы не используем циклы (for/while), только Stream.

Альтернатива без сеттера — собирать новый список с заменой объектов на новые с age+1 (тогда в Dog/Cat нужен конструктор копирования с новым возрастом), но по формулировке задания достаточно «увеличить возраст» через Stream без явных циклов.

#### Шаг 2. Полный фрагмент Main (ввод как в задании 1, вывод после изменения)

```java
// после заполнения списка pets (dog/cat, проверки типа и возраста)

pets.stream()
    .filter(pet -> pet.getAge() > 10)
    .forEach(pet -> pet.setAge(pet.getAge() + 1));

pets.forEach(System.out::println);
```

### Важно

- Запрещены операторы повторения — не используем `for`, `while`, `do-while`, только Stream API.
- Увеличиваем возраст только если `age > 10` (строго больше). Для возраста 10 не увеличиваем.

---

## Задание 5. Отслеживание прогулок питомцев

### Что нужно сделать

- У каждого питомца вызывается метод `goToWalk()`: он вычисляет время прогулки в секундах, «спит» это время (`TimeUnit.SECONDS.sleep(...)`), возвращает это время.
- Вызовы `goToWalk()` выполняются **асинхронно** (каждый в своём потоке).
- Главный поток дожидается завершения всех прогулок.
- Вывести для каждого: информация о питомце, время старта и время конца прогулки **относительно старта программы**. Разница между стартами разных питомцев не более 1 секунды.

### Формулы времени прогулки

- Dog: время = возраст × 0.5 (секунды).
- Cat: время = возраст × 0.25 (секунды).

### Пошаговое решение

#### Шаг 1. Animal с абстрактным методом goToWalk()

```java
public abstract class Animal {
    private final String name;
    private final int age;

    public Animal(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() { return name; }
    public int getAge() { return age; }

    public abstract double goToWalk();
}
```

#### Шаг 2. Dog — goToWalk с sleep

```java
import java.util.concurrent.TimeUnit;

public class Dog extends Animal {

    public Dog(String name, int age) {
        super(name, age);
    }

    @Override
    public double goToWalk() {
        double seconds = getAge() * 0.5;
        try {
            TimeUnit.SECONDS.sleep((long) seconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
        return seconds;
    }

    @Override
    public String toString() {
        return "Dog name = " + getName() + ", age = " + getAge();
    }
}
```

#### Шаг 3. Cat — goToWalk с sleep

```java
@Override
public double goToWalk() {
    double seconds = getAge() * 0.25;
    try {
        TimeUnit.SECONDS.sleep((long) seconds);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        throw new RuntimeException(e);
    }
    return seconds;
}
```

#### Шаг 4. Запуск прогулок в потоках и ожидание

- Зафиксировать время старта программы: `long startMillis = System.currentTimeMillis();`
- Для каждого питомца запустить поток, который:
  - запоминает время старта прогулки (сразу после старта потока);
  - вызывает `pet.goToWalk()`;
  - запоминает время конца;
  - выводит строку: питомец, start time, end time (в секундах относительно startMillis).

Чтобы разница между стартами была не больше 1 секунды, потоки нужно запустить почти одновременно (например, создать все задачи и запустить их подряд, без sleep между запусками).

Пример с ExecutorService и Future:

```java
import java.util.concurrent.*;

long startMillis = System.currentTimeMillis();
ExecutorService executor = Executors.newFixedThreadPool(pets.size());
List<Future<double[]>> futures = new ArrayList<>();

for (Animal pet : pets) {
    Future<double[]> future = executor.submit(() -> {
        double startSec = (System.currentTimeMillis() - startMillis) / 1000.0;
        double duration = pet.goToWalk();
        double endSec = (System.currentTimeMillis() - startMillis) / 1000.0;
        return new double[]{startSec, endSec};
    });
    futures.add(future);
}

// Собрать результаты и вывести (порядок может не совпадать с порядком pets)
// По заданию нужно вывести: "Dog name = ..., start time = ..., end time = ..."
```

Сложность: вывод должен быть после завершения каждой прогулки, и порядок в выводе может быть произвольным (как кто успел). В примерах вывода в задании порядок по завершению. Поэтому удобно внутри задачи (в потоке) после `goToWalk()` сразу вычислить start/end и вывести строку. Тогда синхронизация вывода: обернуть вывод в `synchronized (System.out)` или использовать блокировку, чтобы строки не перемешивались.

Упрощённый вариант: в каждом потоке после возврата из `goToWalk()` вычислить start/end и напечатать в одном блоке:

```java
for (Animal pet : pets) {
    executor.submit(() -> {
        double startSec = (System.currentTimeMillis() - startMillis) / 1000.0;
        double duration = pet.goToWalk();
        double endSec = (System.currentTimeMillis() - startMillis) / 1000.0;
        String line = pet + ", start time = " + String.format("%.2f", startSec) + ", end time = " + String.format("%.2f", endSec);
        synchronized (System.out) {
            System.out.println(line);
        }
    });
}
executor.shutdown();
executor.awaitTermination(1, TimeUnit.HOURS);
```

Так мы дожидаемся всех потоков и выводим время относительно старта программы. Старты будут близки по времени (в пределах секунды), если запускаем все задачи подряд.

#### Шаг 5. Формат вывода

По примеру: `Dog name = Snowball2, age = 8, start time = 0.20, end time = 4.20` — то есть после обычного `toString()` питомца добавляется `, start time = ..., end time = ...` с двумя знаками после запятой.

#### Шаг 6. Полный пример Main для задания 5

```java
import java.util.*;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        List<Animal> pets = new ArrayList<>();
        int count = readInt(scanner);

        for (int i = 0; i < count; i++) {
            String type = scanner.nextLine().trim().toLowerCase();
            if (!type.equals("dog") && !type.equals("cat")) {
                System.out.println("Incorrect input. Unsupported pet type");
                continue;
            }
            String name = scanner.nextLine().trim();
            int age = readInt(scanner);
            if (age <= 0) {
                System.out.println("Incorrect input. Age <= 0");
                continue;
            }
            pets.add(type.equals("dog") ? new Dog(name, age) : new Cat(name, age));
        }
        scanner.close();

        long startMillis = System.currentTimeMillis();
        ExecutorService executor = Executors.newFixedThreadPool(pets.size());

        for (Animal pet : pets) {
            executor.submit(() -> {
                double startSec = (System.currentTimeMillis() - startMillis) / 1000.0;
                pet.goToWalk();
                double endSec = (System.currentTimeMillis() - startMillis) / 1000.0;
                String line = pet + ", start time = " + String.format("%.2f", startSec)
                        + ", end time = " + String.format("%.2f", endSec);
                synchronized (System.out) {
                    System.out.println(line);
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.HOURS);
    }

    private static int readInt(Scanner scanner) {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Could not parse a number. Please, try again");
            }
        }
    }
}
```

### Важно

- `TimeUnit.SECONDS.sleep()` принимает long; время у нас double — приводим к `(long) seconds`.
- Обязательно дождаться завершения всех потоков (`awaitTermination` или `Future.get()` для каждой задачи), иначе программа может закончиться до вывода.

---

## Задание 6. Итератор питомцев

### Что нужно сделать

- Реализовать интерфейс **BaseIterator&lt;T&gt;** с методами `next()`, `hasNext()`, `reset()`.
- Реализовать класс **AnimalIterator**, который обходит список животных и реализует `BaseIterator<Animal>`.
- Программа должна пройти по списку питомцев **только через этот итератор** и вывести информацию о каждом.

### Пошаговое решение

#### Шаг 1. Интерфейс BaseIterator

```java
public interface BaseIterator<T> {
    T next();
    boolean hasNext();
    void reset();
}
```

#### Шаг 2. Класс AnimalIterator

Поля: список животных, индекс текущего элемента. В конструкторе сохраняем список. `next()` возвращает элемент по текущему индексу и увеличивает индекс. `hasNext()` — индекс < размер списка. `reset()` — обнулить индекс.

```java
import java.util.List;

public class AnimalIterator implements BaseIterator<Animal> {
    private final List<Animal> animals;
    private int index;

    public AnimalIterator(List<Animal> animals) {
        this.animals = animals;
        this.index = 0;
    }

    @Override
    public Animal next() {
        Animal current = animals.get(index);
        index++;
        return current;
    }

    @Override
    public boolean hasNext() {
        return index < animals.size();
    }

    @Override
    public void reset() {
        index = 0;
    }
}
```

#### Шаг 3. Использование в Main

После заполнения списка `pets`:

```java
BaseIterator<Animal> iterator = new AnimalIterator(pets);
while (iterator.hasNext()) {
    Animal pet = iterator.next();
    System.out.println(pet);
}
```

Ввод и проверки — как в задании 1 (dog/cat, проверка типа и возраста, обработка ошибок парсинга).

### Важно

- Не использовать цикл по списку типа `for (Animal pet : pets)` для вывода — только итератор.
- Вызов `next()` при отсутствии следующего элемента не описан в задании — на практике вызывать `next()` только когда `hasNext()` true.

---

## Краткая сводка по заданиям

| Задание | Основные элементы |
|--------|--------------------|
| 1 | Animal (abstract), Dog, Cat, список, валидация ввода, toString |
| 2 | + mass, getFeedInfoKg(), проверка mass > 0, формат вывода с feed |
| 3 | Herbivore, Omnivore, Hamster, GuineaPig, вывод сначала травоядных, потом всеядных |
| 4 | Увеличение age на 1 для age > 10 только через Stream, без циклов |
| 5 | goToWalk() с TimeUnit.SECONDS.sleep, потоки, время старта/конца относительно старта программы |
| 6 | BaseIterator&lt;T&gt;, AnimalIterator, обход списка только итератором |

Удачи в выполнении проекта.
