package de.sauroter.advent2024.sixth;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Maze {
    public record Position(int x, int y) {
        public Position advance(final Position step) {
            return new Position(this.x + step.x, this.y + step.y);
        }

        public Position rotate() {
            assert Math.abs(x) + Math.abs(y) == 1;

            // (0,-1) => ^
            // (1,0) => >
            // (0,1) => v
            // (-1,0) => <
            //noinspection SuspiciousNameCombination
            return new Position(-y, x);
        }

        public PositionRecord record(Position step) {
            return new PositionRecord(this, step);
        }
    }

    public record PositionRecord(Position position, Position step) {
    }

    private final int width;
    private final int height;
    private final Set<Position> obstacles;

    private final Set<Position> visited;
    private final Set<PositionRecord> recorded;

    private Position current;
    private Position step = new Position(0, -1);

    public Maze(final Scanner scanner) {
        var x = 0;
        var y = 0;
        this.obstacles = new HashSet<>();
        this.visited = new HashSet<>();
        this.recorded = new HashSet<>();

        while (scanner.hasNextLine()) {
            x = 0;
            final var line = scanner.nextLine().toCharArray();
            for (char c : line) {
                if (c == '#') {
                    obstacles.add(new Position(x, y));
                } else if (c == '^') {
                    current = new Position(x, y);
                }
                x++;
            }
            y++;
        }

        assert current != null;

        visited.add(current);
        recorded.add(current.record(step));
        this.width = x;
        this.height = y;
    }

    private Maze(final Maze maze) {
        this.obstacles = new HashSet<>(maze.obstacles);
        this.visited = new HashSet<>(maze.visited);
        this.recorded = new HashSet<>(maze.recorded);
        this.width = maze.width;
        this.height = maze.height;
        this.current = maze.current;
        this.step = maze.step;
    }

    public int solve() {
        var count = 0;
        while (withinBounds() && count < 10000) {
            var next = current.advance(step);

            while (obstacles.contains(next)) {
                step = step.rotate();
                next = current.advance(step);
            }

            final var nextRecord = next.record(step);
            if (recorded.contains(nextRecord)) {
                return -1;
            }
            recorded.add(nextRecord);

            current = next;
            if (withinBounds()) {
                visited.add(current);
            }
            count++;
        }
        return visited.size();
    }

    public int detectLoops() {
        var count = 0;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                final var tmp = new Maze(this);
                tmp.injectObstacle(x, y);
                final var solve = tmp.solve();
                if (solve < 0) {
                    count++;
                }
            }
        }
        return count;
    }

    private boolean withinBounds() {
        return current.x >= 0 && current.x < width && current.y >= 0 && current.y < height;
    }

    private void injectObstacle(final int x, final int y) {
        this.obstacles.add(new Position(x, y));
    }

    @Override
    public String toString() {
        return "Maze{" +
                "width=" + width +
                ", height=" + height +
                ", obstacles=" + obstacles +
                ", visited=" + visited +
                ", current=" + current +
                ", step=" + step +
                '}';
    }
}
