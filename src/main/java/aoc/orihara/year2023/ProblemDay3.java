package aoc.orihara.year2023;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProblemDay3 {

    private final static String TO_RESOURCES = "/src/main/resources/";
    private final static String NOT_SYMBOLS = "1234567890.";

    class Pair {
        char character;
        boolean used;

        public Pair(final char character) {
            this.character = character;
            this.used = false;
        }
    }


    public void runProblem1() throws IOException {

        int result = 0;

        final File file = new File(System.getProperty("user.dir") + TO_RESOURCES + "day3.input");
        final BufferedReader br = new BufferedReader(new FileReader(file));

        // load structure
        final List<List<Pair>> fullProblem = new ArrayList<>();
        loadStructure(br, fullProblem);
        int i, j;
        for (i = 0; i < fullProblem.size(); i++) {
//        for (i = 0; i < 4; i++) {
            System.out.println("R");
            for (j = 0; j < fullProblem.get(i).size(); j++) {
                System.out.println("Rrrr");
                if (NOT_SYMBOLS.indexOf(fullProblem.get(i).get(j).character) == -1
                        && !fullProblem.get(i).get(j).used) {
                    result += processSymbol(fullProblem, i, j);
                    System.out.println("Raaaa");
                }
            }


        }

        // search symbol
        // search number - prevent multi symbol
        // sum


        System.out.println("Result:" + result);
    }

    private int processSymbol(final List<List<Pair>> fullProblem, final int i, final int j) {
        int result = 0;
        result += processSymbolLine(fullProblem, i - 1, j);
        result += processSymbolLine(fullProblem, i, j);
        result += processSymbolLine(fullProblem, i + 1, j);
        return result;
    }

    private int processSymbolLine(final List<List<Pair>> fullProblem, final int i, final int j) {
        int result = 0;
        if (i >= 0 && i < fullProblem.size()) {
            if (j - 1 >= 0) {
                if (Character.isDigit(fullProblem.get(i).get(j - 1).character)
                        && !fullProblem.get(i).get(j - 1).used) {
                    result += getNumber(fullProblem.get(i), j - 1);
                }
            }
            if (j < fullProblem.get(i).size()) {
                if (Character.isDigit(fullProblem.get(i).get(j).character)
                        && !fullProblem.get(i).get(j).used) {
                    result += getNumber(fullProblem.get(i), j);
                }
            }
            if (j + 1 < fullProblem.get(i).size()) {
                if (Character.isDigit(fullProblem.get(i).get(j + 1).character)
                        && !fullProblem.get(i).get(j + 1).used) {
                    result += getNumber(fullProblem.get(i), j + 1);
                }
            }


        }
        return result;
    }

    private int getNumber(final List<Pair> line, final int location) {
        int startIndex;
        // find start
        for (startIndex = location; startIndex >= 0; startIndex--) {
            if (!Character.isDigit(line.get(startIndex).character)
                    || line.get(startIndex).used) {
                break;
            }
        }
        startIndex++; // ??
        // find end
        int endIndex;
        // find start
        for (endIndex = location; endIndex < line.size(); endIndex++) {
            if (!Character.isDigit(line.get(endIndex).character)
                    || line.get(endIndex).used) {
                break;
            }
        }
        endIndex--; // ??

        // build string
        final StringBuilder theNumber = new StringBuilder();
        for (int i = startIndex; i <= endIndex; i++) {
            line.get(i).used = true;
            theNumber.append(line.get(i).character);
        }
        return Integer.parseInt(theNumber.toString());
    }

    private void loadStructure(final BufferedReader br, final List<List<Pair>> fullProblem) throws IOException {
        String line;
        while ((line = br.readLine()) != null) {
            final List<Pair> lineProblem = new ArrayList<>();
            for (final char c : line.toCharArray()) {
                lineProblem.add(new Pair(c));
            }
            fullProblem.add(lineProblem);
        }
    }

}
