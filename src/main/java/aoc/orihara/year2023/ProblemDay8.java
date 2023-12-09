package aoc.orihara.year2023;

import aoc.orihara.Utils;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

public class ProblemDay8 {


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
//            System.out.println("map: " + s[0] + s1[0] + s1[1]);
        }

        long result = 0;
        String currentStep = "AAA";
//        System.out.println("Path:" + path.length());
        while (!"ZZZ".equals(currentStep)) {
            for (Character a : path.toCharArray()) {
                result++;
                if ("ZZZ".equals(currentStep)) {
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
        // 293 //

        System.out.println("Result:" + result);
    }


}
