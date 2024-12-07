package de.sauroter.advent2024.seventh.two;

import de.sauroter.advent2024.seventh.OperationIterator;

import java.util.Objects;
import java.util.Scanner;
import java.util.function.LongBinaryOperator;

public class Main {

    private enum Operations implements LongBinaryOperator {
        PLUS, MUL, CONCAT;

        @Override
        public long applyAsLong(long left, long right) {
            if (this == PLUS) {
                return left + right;
            } else if (this == MUL) {
                return left * right;
            } else {
                return Long.parseLong("" + left + right);
            }
        }
    }

    public static void main(String[] args) {
        final var inputStream = Main.class.getClassLoader().getResourceAsStream("seventh/input.txt");
        final var scanner = new Scanner(Objects.requireNonNull(inputStream));
        final var main = new Main();

        main.loop(scanner);

        scanner.close();
    }

    // 82042417: 7 5 192 12 8 82 8 1 6 9 1
    void loop(final Scanner scanner) {
        var count = 0L;
        while (scanner.hasNextLine()) {
            final var line = scanner.nextLine();
            final var split = line.split(":");

            final var target = Long.parseLong(split[0]);
            final var stringNumbers = split[1].trim().split(" ");
            final var numbers = new long[stringNumbers.length];
            for (int i = 0; i < stringNumbers.length; i++) {
                numbers[i] = Long.parseLong(stringNumbers[i]);
            }

            count += checkForTarget(target, numbers);
        }

        System.out.println(count);
    }

    private long checkForTarget(final long target, final long[] numbers) {

        final OperationIterator<Operations> operationIterator = new OperationIterator<>(numbers.length - 1, Operations.values());
        while (operationIterator.hasNext()) {
            var current = numbers[0];
            final Operations[] operations = operationIterator.next();

            for (int i = 0; i < operations.length; i++) {
                current = operations[i].applyAsLong(current, numbers[i + 1]);
                if (current > target) {
                    break;
                }
            }
            if (current == target) {
                System.out.println(target);
                return target;
            }
        }
        return 0;
    }
}
