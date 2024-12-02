package de.sauroter.advent2024.second.one;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        final var inputStream = Main.class.getClassLoader().getResourceAsStream("second/input.txt");
        final var scanner = new Scanner(Objects.requireNonNull(inputStream));
        final var main = new Main();

        main.loop(scanner);

        scanner.close();

    }

    void loop(final Scanner scanner) {
        final var lineTokens = new ArrayList<String[]>();
        while (scanner.hasNextLine()) {
            final var line = scanner.nextLine();
            final var split = line.split(" ");
            lineTokens.add(split);
        }


        final var reports = new ArrayList<int[]>(lineTokens.size());
        for (final String[] lineToken : lineTokens) {
            final var report = new int[lineToken.length];
            for (int i = 0; i < lineToken.length; i++) {
                report[i] = Integer.parseInt(lineToken[i]);
            }
            reports.add(report);
        }

        var count = 0;

        for (final int[] report : reports) {
            if (isSafe(report)) {
                count++;
            }
        }

        System.out.println(count);
    }

    boolean isSafe(final int[] report) {

        if (report.length <= 1) {
            return true;
        }

        final boolean increasing = report[0] < report[1];

        for (int i = 1; i < report.length; i++) {
            final var last = report[i - 1];
            final var current = report[i];

            if ((last < current) != increasing) {
                return false;
            }

            final var diff = Math.abs(last - current);
            if (diff == 0 || diff > 3) {
                return false;
            }
        }
        return true;
    }
}