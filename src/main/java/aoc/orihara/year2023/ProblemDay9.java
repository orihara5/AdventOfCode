package aoc.orihara.year2023;

import aoc.orihara.Utils;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProblemDay9 {


    public void runProblem() throws Exception {

        final BufferedReader br = Utils.getFile2023("day9.input");

        long result = 0;
//        List<List<Long>> puzzle = new ArrayList<>();

        String line;
//        line = br.readLine();
        while ((line = br.readLine()) != null) {
//            System.out.println("aaaaaaaaaaaaaaa");
            result += getExtrapolatedValue(Arrays.stream(line.split(" "))
                    .map(Long::parseLong)
                    .toList());

        }

        System.out.println("Result:" + result);
    }

    private long getExtrapolatedValue(List<Long> input) {
//        System.out.println(input);
        List<Long> list = new ArrayList<>();
        // calculate difference
        for (int i = 0; i < input.size() - 1; i++) {
            list.add(input.get(i + 1) - input.get(i));
        }
//        System.out.println(input.size() + " size " + list.size());
        // recurcive call
        if (list.stream().allMatch(aLong -> aLong.equals(0L))) {
//            System.out.println(list.getLast());
            return input.getLast();
        } else {
            long a = input.getLast() + getExtrapolatedValue(list);
//            System.out.println(a);
            return a;
        }
    }


}
