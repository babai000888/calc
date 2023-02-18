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
        } else throw new RuntimeException("Неположительный результат операции с римскими цифрами: " + result);
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
                throw new RuntimeException("нет операции");
        }
    }

    private static char getOperation(String s) {
        if (s.contains("+")) return '+';
        if (s.contains("-")) return '-';
        if (s.contains("*")) return '*';
        if (s.contains("/")) return '/';
        throw new RuntimeException("нет операции");
    }

    private static Formula getNumbers(Formula formula) {
        try {
            formula.firstInt = Integer.parseInt(formula.firstString);
            formula.secondInt = Integer.parseInt(formula.secondString);
            if (isValid(formula.firstInt) && isValid(formula.secondInt)) {
                System.out.println("Валидные арабские цифры: " + formula.firstString + " , " + formula.secondString + " ; можно делать операцию");
                return formula;
            } else {
                throw new RuntimeException("Эти арабские цифры не от 1 до 10: " + formula.firstInt + " , " + formula.secondString);
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

    private static int isValid(String number) {
        switch (number) {
            case "I":
                return 1;
            case "II":
                return 2;
            case "III":
                return 3;
            case "IV":
                return 4;
            case "V":
                return 5;
            case "VI":
                return 6;
            case "VII":
                return 7;
            case "VIII":
                return 8;
            case "IX":
                return 9;
            case "X":
                return 10;
            default:
                throw new RuntimeException("Это не арабская цифра и не римская от I до X: " + number);
        }
    }
}
