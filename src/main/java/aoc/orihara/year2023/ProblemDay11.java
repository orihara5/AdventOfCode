package aoc.orihara.year2023;

import aoc.orihara.Utils;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

public class ProblemDay11 {

    class Location {
        int line;
        int column;
        int iteration = 0;

        public Location(int line, int column) {
            this.line = line;
            this.column = column;
        }

        @Override
        public String toString() {
            return "{" + line +
                    "--" + column +
                    '}';
        }
    }

    public void runProblem() throws Exception {

        final BufferedReader br = Utils.getFile2023("day11.input");

        long result = 0;

        List<List<Character>> map = new ArrayList<>();
        List<Location> universe = new ArrayList<>();

        String line;
//        line = br.readLine();

        int lineNum = 0;
        int maxColumn = 0;
        while ((line = br.readLine()) != null) {
//            map.add(line.chars().mapToObj(e -> (char) e).toList());
            List<Character> lineChar = line.chars().mapToObj(e -> (char) e).toList();
            for (int i = 0; i < lineChar.size(); i++) {
                if (lineChar.get(i) == '#') {
                    universe.add(new Location(lineNum, i));
                    maxColumn = Math.max(maxColumn, i);
                }
            }
            lineNum++;
        }
        int maxLine = lineNum;
        System.out.println("universe:" + universe);
        // expand cosmos
//        map = expandCosmos(map);
        universe = expandCosmos(universe, maxLine, maxColumn);
        System.out.println("universe:" + universe);

        // calculate distance
        for (int i = 0; i < universe.size(); i++) {
            for (int j = i; j < universe.size(); j++) {
                result += calculateDistance(universe.get(i), universe.get(j));
            }
        }


        System.out.println("Result:" + result);
        // 27292467
    }

    private long calculateDistance(Location a, Location b) {

        return Math.abs(a.line - b.line) + Math.abs(a.column - b.column);
    }

    private List<Location> expandCosmos(List<Location> universe, int maxLine, int maxColumn) {
        // expand lines
        List<Integer> lines = universe.stream()
                .map(location -> location.line)
                .distinct()
                .sorted()
                .toList();
        int increment = 0;
        int previous = -1;
        for (int i : lines) {
            increment += (i - previous - 1);
            for (Location location : universe) {
                if (location.line == i && location.iteration == 0) {
                    location.line += increment;
                    location.iteration = 1;
                }
            }
            previous = i;
        }

        // expand column
        List<Integer> columns = universe.stream()
                .map(location -> location.column)
                .distinct()
                .sorted()
                .toList();
        increment = 0;
        previous = 0;
        for (int i : columns) {
            increment += (i - previous - 1);
            for (Location location : universe) {
                if (location.column == i && location.iteration == 1) {
                    location.column += increment;
                    location.iteration = 2;
                }
            }
            previous = i;
        }

        return universe;
    }

    private List<List<Character>> expandCosmos(List<List<Character>> map) {
        List<List<Character>> mapNew = new ArrayList<>();
        for (List<Character> line : map) {
            if (line.stream().allMatch(character -> character.equals('.'))) {
                mapNew.add(line);
            }
            mapNew.add(line);
        }

        for (int i = mapNew.get(0).size() - 1; i >= 0; i--) { // column
            boolean hasPlanet = false;
            for (int j = 0; j < mapNew.size(); j++) {
                if (mapNew.get(j).get(i) != '.') {
                    hasPlanet = true;
                }
            }
            if (!hasPlanet) {
                for (int j = 0; j < mapNew.size(); j++) {
                    mapNew.get(j).add(i, mapNew.get(j).get(i));
                }
            }
        }
        return mapNew;
    }

}
