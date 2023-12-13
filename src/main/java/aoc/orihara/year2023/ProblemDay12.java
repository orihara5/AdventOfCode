package aoc.orihara.year2023;

import aoc.orihara.Utils;

import java.io.BufferedReader;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProblemDay12 {


    public void runProblem() throws Exception {

        final BufferedReader br = Utils.getFile2023("day12.input");

        long result = 0;

        String line;
//        line = br.readLine();

        while ((line = br.readLine()) != null) {
            String[] lineSplit = line.split(" ");
            List<Integer> numbers = Arrays.stream(lineSplit[1].split(",")).map(Integer::parseInt).toList();
            // convert to numbers
            String asNumbers = lineSplit[0].replace('?', '0').replace('#', '1').replace('.', '2');
            result += calculateMatch(asNumbers, numbers);

//            System.out.println("aaa:" + result);

        }

        System.out.println("Result:" + result);
    }

    private int calculateMatch(String s, List<Integer> numbers) {
        Pattern p = Pattern.compile(createRegex(numbers));
        return calculateMatch(s, p, 0);
    }

    private int calculateMatch(String s, Pattern pattern, int index) {
        Matcher m = pattern.matcher(s);

        if (m.matches()) {
            List<Character> cl = s.chars().mapToObj(e -> (char) e).toList();
            int countSubMatches = 0;
            for (int i = index; i < cl.size(); i++) {
                Character c = cl.get(i);
                if (c == '0') {
                    StringBuilder string = new StringBuilder(s);
                    string.setCharAt(i, '1');
                    countSubMatches += calculateMatch(string.toString(), pattern, i + 1);
                }
            }
//            System.out.println("++:" + s);
            Matcher m2 = pattern.matcher(s.replace('0', '2'));
            return countSubMatches + (m2.matches() ? 1 : 0);
        } else {
//            System.out.println("--:" + s);
            return 0;
        }
    }

    private String createRegex(List<Integer> numbers) {
        // search for '0' and '1' to match the numbers (converted)
//        "[^1]*[10]{1}[02]+[10]{3}[02]+[10]{1}[02]+[10]{6}[^1]*"
        StringBuilder res = new StringBuilder();
        res.append("[^1]*");
        boolean first = true;
        for (Integer a : numbers) {
            if (!first) {
                res.append("[02]+");
            } else {
                first = false;
            }
            res.append("[10]{").append(a).append("}");
        }

        res.append("[^1]*");
        return res.toString();
    }

}
