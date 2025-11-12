package ua.opnu;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) {
        System.out.println("Лабораторна робота 7: Лямбда-вирази. Параметризація поведінки\n");

        System.out.println("Завдання 1: Предикат для перевірки простого числа");
        Predicate<Integer> isPrime = n -> {
            if (n == null || n < 2) return false;
            if (n % 2 == 0) return n == 2;
            for (int d = 3; d * d <= n; d += 2)
                if (n % d == 0) return false;
            return true;
        };
        int[] test = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 13, 17, 20};
        System.out.println("Вхідний масив: " + Arrays.toString(test));
        System.out.print("Прості числа: ");
        boolean first = true;
        for (int value : test) {
            if (isPrime.test(value)) {
                if (!first) System.out.print(", ");
                System.out.print(value);
                first = false;
            }
        }
        System.out.println("\n");

        System.out.println("Завдання 2: Фільтрація масиву студентів");
        Student[] students = {
                new Student("Іван Петренко", "Група 1", new int[]{75, 80, 65, 90}),
                new Student("Марія Коваленко", "Група 1", new int[]{55, 60, 58, 62}),
                new Student("Олександр Сидоренко", "Група 2", new int[]{85, 90, 88, 92}),
                new Student("Олена Мельник", "Група 2", new int[]{45, 50, 55, 48}),
                new Student("Дмитро Шевченко", "Група 1", new int[]{70, 75, 80, 85})
        };
        Predicate<Student> hasNoDebts = student -> {
            if (student == null || student.getMarks() == null) return false;
            for (int m : student.getMarks())
                if (m < 60) return false;
            return true;
        };
        Student[] res = filter(students, hasNoDebts);
        System.out.println("Студенти без заборгованостей:");
        for (Student s : res) System.out.println("  " + s.getName());
        System.out.println();

        System.out.println("Завдання 3: Фільтрація за двома предикатами");
        Predicate<Student> fromGroup1 = s -> s != null && "Група 1".equals(s.getGroup());
        Predicate<Student> hasHighMarks = s -> {
            if (s == null || s.getMarks() == null || s.getMarks().length == 0) return false;
            int sum = 0;
            for (int m : s.getMarks()) sum += m;
            return sum / s.getMarks().length >= 75;
        };
        Student[] res2 = filterByTwo(students, fromGroup1, hasHighMarks);
        System.out.println("Результат:");
        for (Student s : res2) System.out.println("  " + s.getName());
        System.out.println();

        System.out.println("Завдання 4: Consumer для виводу студентів");
        Consumer<Student> printStudentName = student -> {
            if (student == null || student.getName() == null) return;
            String[] parts = student.getName().split("\\s+");
            if (parts.length >= 2)
                System.out.println("  " + parts[1].toUpperCase() + " + " + parts[0]);
            else
                System.out.println("  " + student.getName().toUpperCase());
        };
        forEach(students, printStudentName);
        System.out.println();

        System.out.println("Завдання 5: Метод з Predicate та Consumer");
        int[] test2 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        Predicate<Integer> isEven = value -> value % 2 == 0;
        Consumer<Integer> printSquare = value -> System.out.println("  " + value + "² = " + (value * value));
        processIf(test2, isEven, printSquare);
        System.out.println();

        System.out.println("Завдання 6: Function для обчислення 2^n");
        Function<Integer, Integer> powerOfTwo = n -> 1 << n;
        int[] test3 = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        int[] result = processArray(test3, powerOfTwo);
        System.out.println("2^n: " + Arrays.toString(result));
        System.out.println();

        System.out.println("Завдання 7: Метод stringify()");
        Function<Integer, String> numberToString = n -> {
            String[] words = {"нуль", "один", "два", "три", "чотири", "п'ять", "шість", "сім", "вісім", "дев'ять"};
            if (n >= 0 && n <= 9) return words[n];
            return String.valueOf(n);
        };
        String[] stringified = stringify(test3, numberToString);
        System.out.println("Перетворення чисел у слова: " + Arrays.toString(stringified));
        System.out.println("\nВсі завдання виконано успішно!");
    }

    public static Student[] filter(Student[] input, Predicate<Student> p) {
        if (input == null || p == null) return new Student[0];
        Student[] buf = new Student[input.length];
        int counter = 0;
        for (Student s : input)
            if (p.test(s)) buf[counter++] = s;
        return Arrays.copyOf(buf, counter);
    }

    public static Student[] filterByTwo(Student[] input, Predicate<Student> p1, Predicate<Student> p2) {
        if (input == null || p1 == null || p2 == null) return new Student[0];
        Student[] buf = new Student[input.length];
        int counter = 0;
        for (Student s : input)
            if (p1.test(s) && p2.test(s)) buf[counter++] = s;
        return Arrays.copyOf(buf, counter);
    }

    public static void forEach(Student[] input, Consumer<Student> action) {
        if (input == null || action == null) return;
        for (Student s : input) action.accept(s);
    }

    public static void processIf(int[] input, Predicate<Integer> predicate, Consumer<Integer> action) {
        if (input == null || predicate == null || action == null) return;
        for (int v : input)
            if (predicate.test(v)) action.accept(v);
    }

    public static int[] processArray(int[] input, Function<Integer, Integer> fn) {
        if (input == null || fn == null) return new int[0];
        int[] out = new int[input.length];
        for (int i = 0; i < input.length; i++) out[i] = fn.apply(input[i]);
        return out;
    }

    public static String[] stringify(int[] input, Function<Integer, String> fn) {
        if (input == null || fn == null) return new String[0];
        String[] out = new String[input.length];
        for (int i = 0; i < input.length; i++) out[i] = fn.apply(input[i]);
        return out;
    }
}
