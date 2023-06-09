import java.util.Map;
import java.util.Scanner;

public class Calculator {

    private static class Formula {
        String firstString;
        int firstInt;
        String secondString;
        int secondInt;
        char operation;
        int indexOperation;
        int result;
    }

    public static void main(String[] args) {
        Formula formula = new Formula();

        System.out.println("ВВОДИТЕ");
        String line = new Scanner(System.in).nextLine().replaceAll(" ", "");
        formula.operation = getOperation(line);
        formula.indexOperation = line.indexOf(formula.operation);
        formula.firstString = line.substring(0, formula.indexOperation);
        formula.secondString = line.substring(formula.indexOperation + 1);

        System.out.println("Формула: " + line);
        System.out.println("Первое число: " + formula.firstString);
        System.out.println("Операция: " + formula.operation);
        System.out.println("Второе число: " + formula.secondString);

        formula = getNumbers(formula);
        System.out.println("Числа: " + formula.firstInt + " " + formula.secondInt);
        formula.result = calculate(formula);

        try {
            System.out.print("РЕЗУЛЬТАТ: " + formula.firstString + " " + formula.operation + " " + formula.secondString + " = ");
            formula.firstInt = Integer.parseInt(formula.firstString);
            System.out.println(formula.result);
        } catch (Exception e) {
            System.out.println(toRomanian(formula.result));
        }

    }

    private static String toRomanian(int result) {
        int[] arabian = new int[]{100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] romanian = new String[]{"C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        int i = 0;
        StringBuilder sb = new StringBuilder();

        while (result > 0) {
            if (arabian[i] <= result) {    // i - элемент массива арабских цифр меньше либо равен числу res то
                result -= arabian[i];   // от числа вычитаем его эквивалент в массиве арабском
                sb.append(romanian[i]);
            }     // в строку записываем его римское значение
            else {
                i++;
            }
        }
        if (sb.length() > 0) {
            return sb.toString();
        } else throw new IllegalArgumentException("Неположительный результат операции с римскими цифрами: " + result);
    }

    private static int calculate(Formula formula) {
        switch (formula.operation) {
            case '+':
                return formula.firstInt + formula.secondInt;
            case '-':
                return formula.firstInt - formula.secondInt;
            case '*':
                return formula.firstInt * formula.secondInt;
            case '/':
                return (int) (formula.firstInt / formula.secondInt + 0.5);
            default:
                throw new IllegalArgumentException("нет операции");
        }
    }

    private static char getOperation(String s) {
        if (s.contains("+")) return '+';
        if (s.contains("-")) return '-';
        if (s.contains("*")) return '*';
        if (s.contains("/")) return '/';
        throw new IllegalArgumentException("нет операции");
    }

    private static Formula getNumbers(Formula formula) {
        try {
            formula.firstInt = Integer.parseInt(formula.firstString);
            formula.secondInt = Integer.parseInt(formula.secondString);
            if (isValid(formula.firstInt) && isValid(formula.secondInt)) {
                System.out.println("Валидные арабские цифры: " + formula.firstString + " , " + formula.secondString + " ; можно делать операцию");
                return formula;
            } else {
                throw new IllegalArgumentException("Эти арабские цифры не от 1 до 10: " + formula.firstInt + " , " + formula.secondString);
            }
        } catch (NumberFormatException e) {
            System.out.println("Это не арабские цифры..." + e);
            return romanian(formula);
        }
    }

    public static Formula romanian(Formula formula) {
        formula.firstInt = isValid(formula.firstString);
        formula.secondInt = isValid(formula.secondString);
        return formula;
    }

    private static boolean isValid(int num) {
        return num > 0 && num < 11;
    }

    public static int isValid(String number) {
        Map<String, Integer> map = Map.of("I", 1, "II",2, "III", 3, "IV", 4, "V", 5, "VI", 6, "VII", 7, "VIII", 8, "IX", 9, "X", 10);
        try {
            return map.get(number);
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("Это не арабская цифра и не римская от I до X: " + number, e);
        }
    }
}
