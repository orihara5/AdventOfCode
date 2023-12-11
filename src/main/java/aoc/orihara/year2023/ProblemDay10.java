package aoc.orihara.year2023;

import aoc.orihara.Utils;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

public class ProblemDay10 {

    class Node {
        char c;
        int left = 0;
        int up = 0;
        int right = 0;
        int down = 0;
        // 0 - no connection
        // 1 - connection
        // 2 - used connection

        public Node(char c) {
            this.c = c;
            if (c == '|') {
                up = 1;
                down = 1;
            } else if (c == '-') {
                left = 1;
                right = 1;
            } else if (c == 'L') {
                up = 1;
                right = 1;
            } else if (c == 'J') {
                left = 1;
                up = 1;
            } else if (c == '7') {
                left = 1;
                down = 1;
            } else if (c == 'F') {
                right = 1;
                down = 1;
            } else if (c == '.') {
            } else if (c == 'S') {
                // special
            }
        }
    }

    class Pair {
        int vertical;
        int horizontal;

        public Pair(int vertical, int horizontal) {
            this.vertical = vertical;
            this.horizontal = horizontal;
        }
    }

    public void runProblem() throws Exception {

        final BufferedReader br = Utils.getFile2023("day10.input");

        long result = 0;

        List<List<Node>> map = new ArrayList<>();

        String line;
//        line = br.readLine();
        int v = -1, h = -1;
        int vi = 0;
        while ((line = br.readLine()) != null) {
//            System.out.println("aaaaaaaaaaaaaaa");
            List<Node> mapLine = new ArrayList<>();
            char[] charLine = line.toCharArray();
            for (int hi = 0; hi < charLine.length; hi++) {
                mapLine.add(new Node(charLine[hi]));
                if (charLine[hi] == 'S') {
                    v = vi;
                    h = hi;
                }
            }
            map.add(mapLine);
            vi++;
        }
        if (v == -1) {
            System.out.println("Critical Failure");
            return;
        }
        Pair node1 = new Pair(v, h);
        Pair node2 = new Pair(v, h);
        // find valid 'S'
        boolean foundOne = false;
        if (h > 1
                && (map.get(v).get(h - 1).c == '-'
                || map.get(v).get(h - 1).c == 'F'
                || map.get(v).get(h - 1).c == 'L')) {
            if (!foundOne) {
                node1 = new Pair(v, h - 1);
                foundOne = true;
            }
            map.get(v).get(h - 1).right = 2;
        }
        if (h < map.get(v).size() - 1
                && (map.get(v).get(h + 1).c == '-'
                || map.get(v).get(h + 1).c == 'J'
                || map.get(v).get(h + 1).c == '7')) {
            if (!foundOne) {
                node1 = new Pair(v, h + 1);
                foundOne = true;
            } else {
                node2 = new Pair(v, h + 1);
            }
            map.get(v).get(h + 1).left = 2;
        }
        if (v > 1
                && (map.get(v - 1).get(h).c == '|'
                || map.get(v - 1).get(h).c == 'F'
                || map.get(v - 1).get(h).c == '7')) {
            if (!foundOne) {
                node1 = new Pair(v - 1, h);
                foundOne = true;
            } else {
                node2 = new Pair(v - 1, h);
            }
            map.get(v - 1).get(h).down = 2;
        }
        if (v < map.size() - 1
                && (map.get(v + 1).get(h).c == '|'
                || map.get(v + 1).get(h).c == 'J'
                || map.get(v + 1).get(h).c == 'L')) {
            node2 = new Pair(v + 1, h);
            map.get(v + 1).get(h).up = 2;
        }
        result++;

        // loop
        while (node1.vertical != node2.vertical || node1.horizontal != node2.horizontal) {
            node1 = nextNode(map, node1);
            node2 = nextNode(map, node2);
            result++;

            if (result > 20000) {
                System.out.println("Critical Failure - emergency brake");
                throw new RuntimeException();
            }
        }


        System.out.println("Result:" + result);
    }

    private Pair nextNode(List<List<Node>> map, Pair node) {
        Pair node1;
        if (map.get(node.vertical).get(node.horizontal).left == 1) {
            node1 = new Pair(node.vertical, node.horizontal - 1);
            map.get(node.vertical).get(node.horizontal - 1).right = 2;
        } else if (map.get(node.vertical).get(node.horizontal).up == 1) {
            node1 = new Pair(node.vertical - 1, node.horizontal);
            map.get(node.vertical - 1).get(node.horizontal).down = 2;
        } else if (map.get(node.vertical).get(node.horizontal).down == 1) {
            node1 = new Pair(node.vertical + 1, node.horizontal);
            map.get(node.vertical + 1).get(node.horizontal).up = 2;
        } else if (map.get(node.vertical).get(node.horizontal).right == 1) {
            node1 = new Pair(node.vertical, node.horizontal + 1);
            map.get(node.vertical).get(node.horizontal + 1).left = 2;
        } else {
            System.out.println("Critical Failure - nextNode");
            throw new RuntimeException();
        }

        return node1;
    }

}
