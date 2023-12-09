package aoc.orihara.year2023;

import aoc.orihara.Utils;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProblemDay8part2 {


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

        long result = 0;
//        String currentStep = "AAA";
//        System.out.println("Path:" + path.length());
        boolean needToContinue = true;
        System.out.println("ZZZ:" + nodes.toString());
        while (needToContinue) {
            for (Character a : path.toCharArray()) {
                result++;
                if (isTheEnd(nodes)) {
                    System.out.println("ZZZ:" + nodes.toString());
                    needToContinue = false;
                    break;
                }
                nodes = progress(map, nodes, a);
                if (result % 10000 == 0) {
                    System.out.println(result + " : " + nodes.toString());
                }
            }
        }
        //

        System.out.println("Result:" + result);
    }

    private boolean isTheEnd(List<String> nodes) {
        for (String node : nodes) {
            if (node.charAt(2) != 'Z') {// going to be lazy and assume size
                return false;
            }

        }
        return true;
    }

    private List<String> progress(Map<String, Pair> map, List<String> nodes, char next) {
        List<String> nextNodes = new ArrayList<>();
//        System.out.println(next + " : " + nodes.toString());
        for (String node : nodes) {
            if ('L' == next) {
                nextNodes.add(map.get(node).left);
            } else {
                nextNodes.add(map.get(node).right);
            }
        }
        return nextNodes;
    }

}
