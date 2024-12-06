package de.sauroter.advent2024.sixth.two;

import de.sauroter.advent2024.sixth.Maze;

import java.util.Objects;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        final var inputStream = Main.class.getClassLoader().getResourceAsStream("sixth/input.txt");
        final var scanner = new Scanner(Objects.requireNonNull(inputStream));
        final var main = new Main();
        
        main.loop(scanner);

        scanner.close();
    }

    void loop(final Scanner scanner) {
        final var maze = new Maze(scanner);


        System.out.println(maze.detectLoops());
    }
}
