package de.sauroter.advent2024.first.two;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        final var inputStream = Main.class.getClassLoader().getResourceAsStream("first/two/input.txt");
        final var scanner = new Scanner(Objects.requireNonNull(inputStream));
        final var main = new Main();

        main.loop(scanner);

        scanner.close();
    }

    void loop(final Scanner scanner) {

        final var left = new HashSet<Integer>();
        final var right = new ArrayList<Integer>();

        while (scanner.hasNextLine()) {
            final var line = scanner.nextLine();
            final var split = line.split(" {3}");
            left.add(Integer.parseInt(split[0]));
            right.add(Integer.parseInt(split[1]));
        }

        final var result = calculateSimilarity(left, right);

        System.out.println(result);
    }

    private static int calculateSimilarity(final HashSet<Integer> left, final ArrayList<Integer> right) {
        var result = 0;
        for (final Integer i : right) {
            if (left.contains(i)) {
                result += i;
            }
        }
        return result;
    }
}