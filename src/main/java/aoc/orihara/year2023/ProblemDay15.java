package aoc.orihara.year2023;

import aoc.orihara.Utils;

import java.io.BufferedReader;

public class ProblemDay15 {

    public void runProblem() throws Exception {

        final BufferedReader br = Utils.getFile2023("day15.input");

        long result = 0;

        String line;
//        line = br.readLine();
        StringBuilder puzzle = new StringBuilder();
        while ((line = br.readLine()) != null) {
            puzzle.append(line);
        }
        String[] puzzle2 = puzzle.toString().split(",");
        for (String p : puzzle2) {
            result += calculateHash(p);
        }

        // 17828 L
        System.out.println("Result:" + result);
    }

    private long calculateHash(String p) {
        long val = 0;
        for (char c : p.toCharArray()) {
            val = ((val + c) * 17) % 256;

        }
        return val;
    }

}
