package de.sauroter.advent2024.eighth.one;

import de.sauroter.advent2024.eighth.AntennaMatrix;

import java.util.Objects;
import java.util.Scanner;

public class Main {



    public static void main(String[] args) {
        final var inputStream = Main.class.getClassLoader().getResourceAsStream("eighth/input.txt");
        final var scanner = new Scanner(Objects.requireNonNull(inputStream));
        final var main = new Main();

        main.loop(scanner);

        scanner.close();
    }

    void loop(final Scanner scanner) {
        final var antennaMatrix = new AntennaMatrix(scanner);

        final var positions = antennaMatrix.calculateAntidnodes();

        System.out.println(positions);

        antennaMatrix.print();
        System.out.println(positions.size());
    }

}
