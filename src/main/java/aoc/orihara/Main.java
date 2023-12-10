package aoc.orihara;


import aoc.orihara.year2023.ProblemDay9part2;

public class Main {

    public static void main(final String[] args) {

        System.out.printf("\n\n");
        long startTime = System.currentTimeMillis();

        try {
            new ProblemDay9part2().runProblem();
        } catch (final Throwable e) {
            throw new RuntimeException(e);
        }

        System.out.println("Time: " + (System.currentTimeMillis() - startTime));
        System.out.printf("\n\nTHE END");
    }
}