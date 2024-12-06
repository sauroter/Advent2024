package de.sauroter.advent2024.fifth.one;

import java.util.*;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        final var inputStream = Main.class.getClassLoader().getResourceAsStream("fifth/input.txt");
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

        final var rules = new HashMap<Integer, Set<Integer>>();
        final var corrections = new ArrayList<List<Integer>>();
        final var pattern = Pattern.compile("^(\\d{2})\\|(\\d{2})$");
        for (final String line : lines) {
            final var matcher = pattern.matcher(line);
            if (matcher.matches()) {
                final var before = Integer.parseInt(matcher.group(1));
                final var after = Integer.parseInt(matcher.group(2));

                rules.putIfAbsent(before, new HashSet<>());
                rules.get(before).add(after);
            } else if (!line.isBlank()) {
                final var split = line.split(",");
                final var correction = new ArrayList<Integer>();
                corrections.add(correction);
                for (final String element : split) {
                    correction.add(Integer.parseInt(element));
                }
            }
        }

        var sum = 0;
        final var correctionComparator = new CorrectionComparator(rules);
        for (final List<Integer> correction : corrections) {
            if (correctionComparator.isSorted(correction)) {
                System.out.println(correction);
                sum += correction.get(correction.size() / 2);
            }
        }
        System.out.println(sum);
    }

    private static class CorrectionComparator implements Comparator<Integer> {
        final HashMap<Integer, Set<Integer>> rules;

        private CorrectionComparator(final HashMap<Integer, Set<Integer>> rules) {
            this.rules = rules;
        }


        @Override
        public int compare(final Integer o1, final Integer o2) {
            if (Objects.equals(o1, o2)) {
                return 0;
            }
            final var ruleSetO1 = rules.get(o1);
            if (ruleSetO1 != null && ruleSetO1.contains(o2)) {
                return -1;
            }
            final var ruleSetO2 = rules.get(o2);
            if (ruleSetO2 != null && ruleSetO2.contains(o1)) {
                return 1;
            }

            return 0;
        }

        boolean isSorted(final List<Integer> corrections) {
            for (int i = 1; i < corrections.size(); i++) {
                if (this.compare(corrections.get(i - 1), corrections.get(i)) > 0) {
                    return false; // Not sorted
                }
            }
            return true;
        }
    }
}
