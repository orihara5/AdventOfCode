package aoc.orihara.year2023;

import aoc.orihara.Utils;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProblemDay13part2 {


    public void runProblem() throws Exception {

        final BufferedReader br = Utils.getFile2023("day13.input");

        long result = 0;

        String line;
//        line = br.readLine();
        List<String> puzzle = new ArrayList<>();
        int i = 0;
        while ((line = br.readLine()) != null) {
            if (line.isBlank()) {
                System.out.println("board: " + ++i);
                result += runPuzzle(puzzle);
                puzzle = new ArrayList<>();
            } else {
                puzzle.add(line);
            }
        }
        if (!puzzle.isEmpty()) {
            result += runPuzzle(puzzle);
        }

        // 37702 H
        System.out.println("Result:" + result);
    }

    private Long runPuzzleRow(List<String> puzzle) {
        for (int i = 0; i < puzzle.size() - 1; i++) {
            int numberOfDiff = 0;
            boolean isEquals = Objects.equals(puzzle.get(i), puzzle.get(i + 1));
            if (!isEquals) {
                int mistake = hasMistake(puzzle.get(i), puzzle.get(i + 1));
                if (mistake <= 1) {
                    isEquals = true;
                    numberOfDiff++;
                }
            }
            if (isEquals) {
                boolean isFake = false;
                for (int j = 1; i + j + 1 < puzzle.size(); j++) {
                    if ((i - j) < 0) {
                        if (numberOfDiff == 1) {
                            System.out.println("found it");
                            return i + 1L;
                        }
                        break;
                    }
                    numberOfDiff += hasMistake(puzzle.get(i + 1 + j), puzzle.get(i - j));
                    if (numberOfDiff > 1) {
//                        System.out.println("fake");
                        System.out.println("fake");
//                        return i;
                        isFake = true;
                        break;
                    }
                    if (i == j) {
                        if (numberOfDiff == 1) {
                            System.out.println("found it");
                            return i + 1L;
                        }
                        break;
                    }
                }
                if (!isFake) {
                    if (numberOfDiff == 1) {
                        System.out.println("found itqqq");
                        return i + 1L;
                    }
                }
            }
        }
        System.out.println("no mirror");
        return null;
    }

    private int hasMistake(String s, String s1) {
        int mistake = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != s1.charAt(i)) {
                if (mistake == 1) {
                    return 500; // big number
                }
                mistake++;
            }
        }
        return mistake;
    }

    private Long runPuzzleColumn(List<String> puzzle) {
        List<String> puzzleR = rotatePuzzle(puzzle);
        return runPuzzleRow(puzzleR);
    }

    private List<String> rotatePuzzle(List<String> puzzle) {
        List<StringBuilder> puzzleR = new ArrayList<>();
        for (int i = 0; i < puzzle.getFirst().length(); i++) {
            puzzleR.add(new StringBuilder());
        }
        for (String s : puzzle) {
            char[] charArray = s.toCharArray();
            for (int j = 0; j < charArray.length; j++) {
                puzzleR.get(j).append(charArray[j]);
            }
        }
        List<String> puzzleR2 = new ArrayList<>();
        for (int i = 0; i < puzzleR.size(); i++) {
            puzzleR2.add(puzzleR.get(i).toString());
        }
        return puzzleR2;
    }

    private long runPuzzle(List<String> puzzle) {
        System.out.println("runPuzzleRow");
        Long result = runPuzzleRow(puzzle);
        System.out.println(result);
        if (result != null) {
            return result * 100;
        } else {
            System.out.println("runPuzzleColumn");
            result = runPuzzleColumn(puzzle);
            System.out.println(result);
            return result != null ? result : 0;
        }
    }


}
