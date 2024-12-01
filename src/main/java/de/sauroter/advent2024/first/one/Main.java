package de.sauroter.advent2024.first.one;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        final var inputStream = Main.class.getClassLoader().getResourceAsStream("first/one/input.txt");
        final var scanner = new Scanner(Objects.requireNonNull(inputStream));
        final var main = new Main();

        main.loop(scanner);

        scanner.close();

    }

    void loop(final Scanner scanner) {

        final var left = new ArrayList<Integer>();
        final var right = new ArrayList<Integer>();

        while (scanner.hasNextLine()) {
            final var line = scanner.nextLine();
            final var split = line.split(" {3}");
            left.add(Integer.parseInt(split[0]));
            right.add(Integer.parseInt(split[1]));
        }


        left.sort(Integer::compare);
        right.sort(Integer::compare);

        var result = 0;

        for (int i = 0; i < left.size(); i++) {
            result += Math.abs(left.get(i) - right.get(i));
        }

        System.out.println(result);
    }
}