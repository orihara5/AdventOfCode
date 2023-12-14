package aoc.orihara.year2023;

import aoc.orihara.Utils;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProblemDay13 {


    public void runProblem() throws Exception {

        final BufferedReader br = Utils.getFile2023("day13.input.test");

        long result = 0;

        String line;
//        line = br.readLine();
        List<String> puzzle = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            if (line.isBlank()) {
                result += runPuzzle(puzzle);
                puzzle = new ArrayList<>();
            } else {
                puzzle.add(line);
            }
        }
        if (!puzzle.isEmpty()) {
            result += runPuzzle(puzzle);
        }

        // 17828 L
        System.out.println("Result:" + result);
    }

    private long runPuzzleRow(List<String> puzzle) {
        System.out.println("runPuzzleRow");
        for (int i = 0; i < puzzle.size() - 1; i++) {
            if (Objects.equals(puzzle.get(i), puzzle.get(i + 1))) {
                boolean isFake = false;
                for (int j = 1; i + j + 1 < puzzle.size(); j++) {
                    if ((i - j) < 0) {
                        System.out.println("found it");
                        return i + 1;
                    }
                    if (!Objects.equals(puzzle.get(i + 1 + j), puzzle.get(i - j))) {
//                        System.out.println("fake");
                        System.out.println("fake");
//                        return i;
                        isFake = true;
                        break;
                    }
                    if (i == j) {
                        System.out.println("found it");
                        return i + 1;
                    }
                }
                if (!isFake) {
                    return i + 1;
                }
            }
        }
        System.out.println("no mirror");
        return 0;
    }

    private long runPuzzleColumn(List<String> puzzle) {
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
        long result = 100 * runPuzzleRow(puzzle);
        System.out.println(result);
        result += runPuzzleColumn(puzzle);
        System.out.println(result);
        return result;
    }


}
