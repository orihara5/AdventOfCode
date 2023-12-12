package aoc.orihara.year2023;

import aoc.orihara.Utils;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ProblemDay11part2 {

    public static final int UNIVERSE_SIZE = 1000000;

    class Location {
        Integer line;
        Integer column;
        int iteration = 0;

        public Location(int line, int column) {
            this.line = line;
            this.column = column;
        }

        @Override
        public String toString() {
            return "{" + line +
                    " " + column +
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
        Integer previous = null;
        for (int i : lines) {
            if (previous == null) {
                previous = i;
                continue;
            }
            int dif = i - previous;
            if (dif != 1) {
                increment += (dif - 1) * UNIVERSE_SIZE - dif + 1;
            }
            System.out.println("increment:" + increment);
            for (Location location : universe) {
                if (location.line == i && location.iteration == 0) {
                    location.line += increment;
                    location.iteration = 1;
                }
            }
            previous = i;
        }

        System.out.println("universe:" + universe);
        // expand column
        List<Integer> columns = universe.stream()
                .map(location -> location.column)
                .distinct()
                .sorted()
                .toList();
        increment = 0;
        previous = null;

        System.out.println("universe2:" + universe.stream().sorted(Comparator.comparing(o -> o.column)).toList());
        for (int i : columns) {
            if (previous == null) {
                previous = i;
                continue;
            }
            int dif = i - previous;
            if (dif != 1) {
                increment += (dif - 1) * UNIVERSE_SIZE - dif + 1;
            }
            System.out.println("increment:" + increment);
            for (Location location : universe) {
                if (location.column == i && location.iteration < 2) {
                    location.column += increment;
                    location.iteration = 2;
                }
            }
            previous = i;
        }

        System.out.println("universe2:" + universe.stream().sorted(Comparator.comparing(o -> o.column)).toList());
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
