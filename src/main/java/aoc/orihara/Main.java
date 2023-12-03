package aoc.orihara;


import aoc.orihara.year2023.ProblemDay3part2;

public class Main {
    public static void main(final String[] args) {

        System.out.printf("\n\n");

        try {
            new ProblemDay3part2().runProblem2();
        } catch (final Throwable e) {
            throw new RuntimeException(e);
        }

        System.out.printf("\n\nTHE END");
    }
}