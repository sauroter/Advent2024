package de.sauroter.advent2024.third.one;

import java.io.IOException;
import java.util.Objects;
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
        record Pair(int first, int second) {
        }
        final var nonWhiteSpaceInstructions = rawInstructions.replaceAll("\\s", "");

        final var pattern = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)");

        final var matcher = pattern.matcher(nonWhiteSpaceInstructions);

        final var matchStream = Stream.generate(() -> {
            if (matcher.find()) {
                return new Pair(Integer.parseInt(matcher.group(1)), Integer.parseInt((matcher.group(2))));
            } else {
                return null;
            }
        }).takeWhile(Objects::nonNull);

        final var total = matchStream.mapToInt(p -> p.first * p.second).reduce(0, Integer::sum);

        System.out.println(total);
    }
}