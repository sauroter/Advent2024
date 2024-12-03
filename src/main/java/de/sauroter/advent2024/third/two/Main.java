package de.sauroter.advent2024.third.two;

import java.io.IOException;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) throws IOException {
        final String rawInstructions;
        try (var inputStream = Main.class.getClassLoader().getResourceAsStream("third/input.txt")) {
            rawInstructions = new String(Objects.requireNonNull(inputStream).readAllBytes());
        }

        final var main = new Main();
        main.loop(rawInstructions);
    }

    void loop(final String rawInstructions) {


        final var matchStream = Stream.generate(new MulGenerator(rawInstructions)).takeWhile(Objects::nonNull);

        final var total = matchStream.mapToInt(p -> p.first * p.second).reduce(0, Integer::sum);

        System.out.println(total);
    }

    private record Pair(int first, int second) {
    }

    private static class MulGenerator implements Supplier<Pair> {
        private static final Pattern pattern = Pattern.compile("(?<mul>mul\\((?<first>\\d{1,3}),(?<second>\\d{1,3})\\))|(?<do>do\\(\\))|(?<dont>don't\\(\\))");

        private final Matcher matcher;
        private boolean on = true;

        private MulGenerator(final String rawInstructions) {
            final var nonWhiteSpaceInstructions = rawInstructions.replaceAll("\\s", "");
            matcher = pattern.matcher(nonWhiteSpaceInstructions);
        }

        @Override
        public Pair get() {
            while (matcher.find()) {
                if (matcher.group("do") != null) {
                    on = true;
                } else if (matcher.group("dont") != null) {
                    on = false;
                } else if (on && matcher.group("mul") != null) {
                    return new Pair(Integer.parseInt(matcher.group("first")), Integer.parseInt((matcher.group("second"))));
                }
            }
            return null;
        }
    }
}