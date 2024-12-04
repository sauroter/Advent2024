package de.sauroter.advent2024.fourth.one;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        final var inputStream = Main.class.getClassLoader().getResourceAsStream("fourth/input.txt");
        final var scanner = new Scanner(Objects.requireNonNull(inputStream));
        final var main = new Main();

        main.loop(scanner);

        scanner.close();

    }

    void loop(final Scanner scanner) {
        final var lines = new ArrayList<String>();
        while (scanner.hasNextLine()) {
            final var line = scanner.nextLine();
            lines.add(line);
        }

        final var matrix = new Matrix(lines, new char[]{'X', 'M', 'A', 'S'});
        System.out.println(matrix.countXmas());
    }

    private static class Matrix {
        private final char[] search;

        private final char[][] array;
        private final int height;
        private final int width;


        private Matrix(final ArrayList<String> lines, final char[] search) {
            height = lines.size();
            array = new char[height][];
            for (int i = 0; i < lines.size(); i++) {
                final var line = lines.get(i);
                array[i] = line.toCharArray();
            }
            width = array[0].length;
            this.search = search;
        }

        private boolean unsafe(int a, int b) {
            return a < 0 ||
                    b < 0 ||
                    a >= height ||
                    b >= width;
        }

        private int right(int a, int b) {
            if (unsafe(a, b + (search.length - 1))) {
                return 0;
            }

            for (int i = 0; i < search.length; i++) {
                if (search[i] != array[a][b + i]) {
                    return 0;
                }
            }
            return 1;
        }

        private int left(int a, int b) {
            if (unsafe(a, b - (search.length - 1))) {
                return 0;
            }

            for (int i = 0; i < search.length; i++) {
                if (search[i] != array[a][b - i]) {
                    return 0;
                }
            }
            return 1;
        }

        private int up(int a, int b) {
            if (unsafe(a - (search.length - 1), b)) {
                return 0;
            }

            for (int i = 0; i < search.length; i++) {
                if (search[i] != array[a - i][b]) {
                    return 0;
                }
            }
            return 1;
        }

        private int down(int a, int b) {
            if (unsafe(a + (search.length - 1), b)) {
                return 0;
            }

            for (int i = 0; i < search.length; i++) {
                if (search[i] != array[a + i][b]) {
                    return 0;
                }
            }
            return 1;
        }

        private int upRight(int a, int b) {
            if (unsafe(a - (search.length - 1), b + (search.length - 1))) {
                return 0;
            }

            for (int i = 0; i < search.length; i++) {
                if (search[i] != array[a - i][b + i]) {
                    return 0;
                }
            }
            return 1;
        }

        private int downRight(int a, int b) {
            if (unsafe(a + (search.length - 1), b + (search.length - 1))) {
                return 0;
            }

            for (int i = 0; i < search.length; i++) {
                if (search[i] != array[a + i][b + i]) {
                    return 0;
                }
            }
            return 1;
        }

        private int downLeft(int a, int b) {
            if (unsafe(a + (search.length - 1), b - (search.length - 1))) {
                return 0;
            }

            for (int i = 0; i < search.length; i++) {
                if (search[i] != array[a + i][b - i]) {
                    return 0;
                }
            }
            return 1;
        }

        private int upLeft(int a, int b) {
            if (unsafe(a - (search.length - 1), b - (search.length - 1))) {
                return 0;
            }

            for (int i = 0; i < search.length; i++) {
                if (search[i] != array[a - i][b - i]) {
                    return 0;
                }
            }
            return 1;
        }

        private int checkForXmas(int a, int b) {
            return right(a, b) +
                    left(a, b) +
                    up(a, b) +
                    down(a, b) +
                    upRight(a, b) +
                    downRight(a, b) +
                    downLeft(a, b) +
                    upLeft(a, b);
        }

        private int countXmas() {
            var count = 0;
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    count += checkForXmas(i, j);
                }
            }
            return count;
        }
    }
}
