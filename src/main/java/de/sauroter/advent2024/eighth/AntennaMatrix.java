package de.sauroter.advent2024.eighth;

import java.util.*;

public class AntennaMatrix {
    public record Position(int row, int col) {
        public Position vectorTo(final Position b) {
            return new Position(b.row - this.row, b.col - this.col);
        }

        public Position negative() {
            return new Position(-this.row, -this.col);
        }

        public Position add(final Position b) {
            return new Position(this.row + b.row, this.col + b.col);
        }
    }

    private final int height;
    private final int width;
    private final Set<Position> antinodes = new HashSet<>();
    private final Map<Character, List<Position>> antennas = new HashMap<>();

    public AntennaMatrix(final Scanner scanner) {

        final List<String> lines = new ArrayList<>();

        while (scanner.hasNextLine()) {
            lines.add(scanner.nextLine());
        }

        var width = 0;
        for (int i = 0; i < lines.size(); i++) {
            final var line = lines.get(i).toCharArray();
            width = line.length;
            for (int j = 0; j < line.length; j++) {
                final var c = line[j];
                if (c != '.') {
                    final var positions = antennas.computeIfAbsent(c, (ignored) -> new ArrayList<>());
                    positions.add(new Position(i, j));
                }
            }
        }
        this.height = lines.size();
        this.width = width;
    }

    private boolean isInBounds(final Position position) {
        return position.row >= 0 && position.row < height && position.col >= 0 && position.col < width;
    }

    public Set<Position> calculateAntinodes() {
        for (final List<Position> antennaPositions : antennas.values()) {
            if (antennaPositions.size() < 2) {
                continue;
            }
            for (int i = 0; i < antennaPositions.size() - 1; i++) {
                final var firstPosition = antennaPositions.get(i);
                for (int j = 1; j < antennaPositions.size(); j++) {
                    final var secondPosition = antennaPositions.get(j);
                    if (Objects.equals(firstPosition, secondPosition)) {
                        continue;
                    }

                    final var vectorTo = firstPosition.vectorTo(secondPosition);
                    final var firstAntinode = firstPosition.add(vectorTo.negative());
                    final var secondAntinode = secondPosition.add(vectorTo);

                    if (isInBounds(firstAntinode)) {
                        antinodes.add(firstAntinode);
                    }

                    if (isInBounds(secondAntinode)) {
                        antinodes.add(secondAntinode);
                    }
                }
            }
        }

        return antinodes;
    }

    public Set<Position> calculateAntinodesWithHarmonics() {
        for (final List<Position> antennaPositions : antennas.values()) {
            if (antennaPositions.size() < 2) {
                continue;
            }
            for (int i = 0; i < antennaPositions.size() - 1; i++) {
                final var firstPosition = antennaPositions.get(i);
                antinodes.add(firstPosition);
                for (int j = 1; j < antennaPositions.size(); j++) {
                    final var secondPosition = antennaPositions.get(j);
                    antinodes.add(secondPosition);
                    if (Objects.equals(firstPosition, secondPosition)) {
                        continue;
                    }
                    final var vectorTo = firstPosition.vectorTo(secondPosition);

                    Position left = firstPosition;
                    while (isInBounds(left = left.add(vectorTo.negative()))) {
                        antinodes.add(left);
                    }

                    Position right = secondPosition;
                    while (isInBounds(right = right.add(vectorTo))) {
                        antinodes.add(right);
                    }
                }
            }
        }
        return antinodes;
    }

    public void print() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                final var position = new Position(i, j);
                if (antinodes.contains(position)) {
                    System.out.print("#");
                } else {
                    System.out.print(".");
                }
            }
            System.out.println();
        }
    }

}
