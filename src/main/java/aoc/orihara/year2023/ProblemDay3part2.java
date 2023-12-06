package aoc.orihara.year2023;

import aoc.orihara.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProblemDay3part2 {

    private final static String SYMBOLS = "*";

    class Pair {
        char character;
        boolean used;

        public Pair(final char character) {
            this.character = character;
            this.used = false;
        }
    }

    class Hold {
        int numbersFound;
    }


    public void runProblem2() throws Exception {

        int result = 0;

        final BufferedReader br = Utils.getFile2023("day3.input");

        // load structure
        final List<List<Pair>> fullProblem = new ArrayList<>();
        loadStructure(br, fullProblem);
        int i, j;
        for (i = 0; i < fullProblem.size(); i++) {
//        for (i = 0; i < 4; i++) {
            System.out.println("R");
            for (j = 0; j < fullProblem.get(i).size(); j++) {
                System.out.println("Rrrr");
                if (SYMBOLS.indexOf(fullProblem.get(i).get(j).character) != -1
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
        int result = 1;
        final Hold numbersFound = new Hold();
        numbersFound.numbersFound = 0;
        result *= processSymbolLine(fullProblem, i - 1, j, numbersFound);
        result *= processSymbolLine(fullProblem, i, j, numbersFound);
        result *= processSymbolLine(fullProblem, i + 1, j, numbersFound);
        if (numbersFound.numbersFound > 1) {
            return result;
        }
        return 0;
    }

    private int processSymbolLine(final List<List<Pair>> fullProblem, final int i, final int j, final Hold numbersFound) {
        int result = 1;
        if (i >= 0 && i < fullProblem.size()) {
            if (j - 1 >= 0) {
                if (Character.isDigit(fullProblem.get(i).get(j - 1).character)
                        && !fullProblem.get(i).get(j - 1).used) {
                    result *= getNumber(fullProblem.get(i), j - 1);
                    numbersFound.numbersFound++;
                }
            }
            if (j < fullProblem.get(i).size()) {
                if (Character.isDigit(fullProblem.get(i).get(j).character)
                        && !fullProblem.get(i).get(j).used) {
                    result *= getNumber(fullProblem.get(i), j);
                    numbersFound.numbersFound++;
                }
            }
            if (j + 1 < fullProblem.get(i).size()) {
                if (Character.isDigit(fullProblem.get(i).get(j + 1).character)
                        && !fullProblem.get(i).get(j + 1).used) {
                    result *= getNumber(fullProblem.get(i), j + 1);
                    numbersFound.numbersFound++;
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
