package aoc.orihara.year2023;

import aoc.orihara.LeastCommonMultipleCalculator;
import aoc.orihara.Utils;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProblemDay8part2V2 {


    class Pair {
        String left;
        String right;

        public Pair(String left, String right) {
            this.left = left;
            this.right = right;
        }
    }

    public void runProblem() throws Exception {

        final BufferedReader br = Utils.getFile2023("day8.input");

        String path = br.readLine();

        Map<String, Pair> map = new HashMap<>();
        String line;
        List<String> nodes = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            if (StringUtils.isEmpty(line)) {
                continue;
            }
            String lineClean = line.replace(" ", "")
                    .replace("(", "")
                    .replace(")", "");

            String[] s = lineClean.split("=");
            String[] s1 = s[1].split(",");
            map.put(s[0], new Pair(s1[0], s1[1]));
            if (s[0].charAt(2) == 'A') {// going to be lazy and assume size
                nodes.add(s[0]);
            }
//            System.out.println("map: " + s[0] + s1[0] + s1[1]);
        }

        List<Long> r = new ArrayList<>();
        for (String node : nodes) {
            long result = 0;
            String currentStep = node;
//        System.out.println("Path:" + path.length());
            while (currentStep.charAt(2) != 'Z') {
                for (Character a : path.toCharArray()) {
                    result++;
                    if (currentStep.charAt(2) == 'Z') {
//                System.out.println("ZZZ:" + result);
                        break;
                    }
                    if ('L' == a) {
                        currentStep = map.get(currentStep).left;
                    } else {
                        currentStep = map.get(currentStep).right;
                    }
                }
            }
            r.add(result);
        }

        // not my idea
        long result = new LeastCommonMultipleCalculator().calculate(r);

        System.out.println("Result:" + result);
    }


}
