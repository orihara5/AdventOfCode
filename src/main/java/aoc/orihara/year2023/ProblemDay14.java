package aoc.orihara.year2023;

import aoc.orihara.Utils;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProblemDay14 {

    public void runProblem() throws Exception {

        final BufferedReader br = Utils.getFile2023("day14.input");

        long result = 0;

        String line;
//        line = br.readLine();
        List<List<Character>> puzzle = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            puzzle.add(line.chars().mapToObj(e -> (char) e).collect(Collectors.toList()));
        }
        // roll up
        puzzle = rollUp(puzzle);

        // calculate
        result = calculateWeight(puzzle);

        // 17828 L
        System.out.println("Result:" + result);
    }

    private List<List<Character>> rollUp(List<List<Character>> puzzle) {
        int charArraySize = puzzle.getFirst().size();
        boolean somethingMoved = true;
        while (somethingMoved) {
            somethingMoved = false;
            for (int i = 0; i < puzzle.size() - 1; i++) {
                for (int j = 0; j < charArraySize; j++) {
                    if (puzzle.get(i).get(j) == '.' && puzzle.get(i + 1).get(j) == 'O') {
                        somethingMoved = true;
                        puzzle.get(i).set(j, 'O');
                        puzzle.get(i + 1).set(j, '.');
                    }
                }
            }
            System.out.println("Result:");
        }
        return puzzle;
    }

    private long calculateWeight(List<List<Character>> puzzle) {
        long res = 0;
        for (int i = 0; i < puzzle.size(); i++) {
            for (Character c : puzzle.get(i)) {
                if (c == 'O') {
                    res += puzzle.size() - i;
                }
            }
        }
        return res;
    }


}
