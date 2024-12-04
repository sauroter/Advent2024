package de.sauroter.advent2024.fourth.two;

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

        final var matrix = new Matrix(lines);
        System.out.println(matrix.countXmas());
    }

    private static class Matrix {

        private final char[][] array;
        private final int height;
        private final int width;


        private Matrix(final ArrayList<String> lines) {
            height = lines.size();
            array = new char[height][];
            for (int i = 0; i < lines.size(); i++) {
                final var line = lines.get(i);
                array[i] = line.toCharArray();
            }
            width = array[0].length;
        }

        private int checkForXmas(int a, int b) {
            final var topRight = array[a - 1][b + 1];
            final var bottomRight = array[a + 1][b + 1];
            final var bottomLeft = array[a + 1][b - 1];
            final var topLeft = array[a - 1][b - 1];

            if (
                    (topRight == 'M' && bottomLeft == 'S' || topRight == 'S' && bottomLeft == 'M')
                            && (topLeft == 'M' && bottomRight == 'S' || topLeft == 'S' && bottomRight == 'M')
            ) {
                return 1;
            }
            return 0;
        }

        private int countXmas() {
            var count = 0;
            for (int i = 1; i < height - 1; i++) {
                for (int j = 1; j < width - 1; j++) {
                    if (array[i][j] == 'A') {
                        count += checkForXmas(i, j);
                    }
                }
            }
            return count;
        }
    }
}
