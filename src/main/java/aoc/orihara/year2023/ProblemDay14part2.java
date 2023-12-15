package aoc.orihara.year2023;

import aoc.orihara.Utils;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProblemDay14part2 {

    public static final int ITERATIONS = 1000000000;

    public void runProblem() throws Exception {

        final BufferedReader br = Utils.getFile2023("day14.input");

        long result = 0;

        String line;
        List<List<Character>> puzzle = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            puzzle.add(line.chars().mapToObj(e -> (char) e).collect(Collectors.toList()));
        }

        List<List<List<Character>>> savedPuzzle = new ArrayList<>();

        // find loop
        long currentIter = 0;
        int foundAtIndex = -1;
        for (long i = 0; i < ITERATIONS; i++) {
            puzzle = rollUp(puzzle);
            puzzle = rollLeft(puzzle);
            puzzle = rollDown(puzzle);
            puzzle = rollRight(puzzle);

            if ((foundAtIndex = getPrimeSavedPuzzle(savedPuzzle, puzzle)) != -1) {
                currentIter = i;
                System.out.println("Puzzle:" + puzzle);
                System.out.println(i + " is  a loop " + i + ":" + puzzle);
                System.out.println(foundAtIndex + " is  a ");
                break;
            }

            savedPuzzle.add(deepCopy(puzzle));
            if (i % 1000000 == 0) {
                System.out.println("a:" + i);
            }
        }
        long cycle = currentIter - foundAtIndex;
        long preCycle = currentIter - cycle;
        long cycleIterations = (ITERATIONS - preCycle) / cycle;
        long toPosCycle = cycleIterations * cycle + preCycle;

        for (long i = toPosCycle + 1; i < ITERATIONS; i++) {
            puzzle = rollUp(puzzle);
            puzzle = rollLeft(puzzle);
            puzzle = rollDown(puzzle);
            puzzle = rollRight(puzzle);
        }

        // calculate
        result = calculateWeight(puzzle);

        // 95189 L
        // 95304 H
        System.out.println("Result:" + result);
    }

    private int getPrimeSavedPuzzle(List<List<List<Character>>> primeSavedPuzzle, List<List<Character>> puzzle) {
        for (int i = 0; i < primeSavedPuzzle.size(); i++) {
            if (primeSavedPuzzle.get(i).equals(puzzle)) {
                return i;
            }
        }
        return -1;
    }

    private List<List<Character>> deepCopy(List<List<Character>> puzzle) {
        List<List<Character>> puzzle2 = new ArrayList<>();
        puzzle.forEach(characters -> {
            List<Character> cList = new ArrayList<>();
            puzzle2.add(cList);
            characters.forEach(character -> cList.add(new Character(character)));
        });
        return puzzle2;
    }

    private List<List<Character>> rollRight(List<List<Character>> puzzle) {
        int charArraySize = puzzle.getFirst().size() - 1;
        boolean somethingMoved = true;
        while (somethingMoved) {
            somethingMoved = false;
            for (int i = 0; i < puzzle.size(); i++) {
                for (int j = 0; j < charArraySize; j++) {
                    if (puzzle.get(i).get(j) == 'O' && puzzle.get(i).get(j + 1) == '.') {
                        somethingMoved = true;
                        puzzle.get(i).set(j, '.');
                        puzzle.get(i).set(j + 1, 'O');
                    }
                }
            }
        }
        return puzzle;
    }

    private List<List<Character>> rollLeft(List<List<Character>> puzzle) {
        int charArraySize = puzzle.getFirst().size() - 1;
        boolean somethingMoved = true;
        while (somethingMoved) {
            somethingMoved = false;
            for (int i = 0; i < puzzle.size(); i++) {
                for (int j = 0; j < charArraySize; j++) {
                    if (puzzle.get(i).get(j) == '.' && puzzle.get(i).get(j + 1) == 'O') {
                        somethingMoved = true;
                        puzzle.get(i).set(j, 'O');
                        puzzle.get(i).set(j + 1, '.');
                    }
                }
            }
        }
        return puzzle;
    }

    private List<List<Character>> rollDown(List<List<Character>> puzzle) {
        int charArraySize = puzzle.getFirst().size();
        boolean somethingMoved = true;
        while (somethingMoved) {
            somethingMoved = false;
            for (int i = puzzle.size() - 2; i >= 0; i--) {
                for (int j = 0; j < charArraySize; j++) {
                    if (puzzle.get(i + 1).get(j) == '.' && puzzle.get(i).get(j) == 'O') {
                        somethingMoved = true;
                        puzzle.get(i + 1).set(j, 'O');
                        puzzle.get(i).set(j, '.');
                    }
                }
            }
        }
        return puzzle;
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
