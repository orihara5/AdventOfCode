package aoc.orihara.year2023;

import aoc.orihara.Utils;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

import static aoc.orihara.year2023.ProblemDay16part2.Direction.*;

public class ProblemDay16part2 {

    enum Direction {
        UP, LEFT, DOWN, RIGHT
    }

    class Beam {
        Direction from;
        int v, h; // location

        public Beam(Direction from, int v, int h) {
            this.from = from;
            this.v = v;
            this.h = h;
        }

        public Beam(Direction from, Beam b) {
            this.from = from;

            if (from.equals(UP)) {
                v = b.v + 1;
                h = b.h;
            } else if (from.equals(DOWN)) {
                v = b.v - 1;
                h = b.h;
            } else if (from.equals(LEFT)) {
                v = b.v;
                h = b.h + 1;
            } else { //RIGHT
                v = b.v;
                h = b.h - 1;
            }
        }
    }

    class Tile {
        char c;
        int battery = 0;
        boolean canSplit = true;

        public Tile(char c) {
            this.c = c;
        }
    }

    public void runProblem() throws Exception {

        final BufferedReader br = Utils.getFile2023("day16.input");

        long result = 0;

        String line;
//        line = br.readLine();
        List<List<Tile>> map = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            List<Tile> t = new ArrayList<>();
            for (char c : line.toCharArray()) {
                t.add(new Tile(c));
            }
            map.add(t);
        }
        int maxV = map.size() - 1;
        int maxH = map.getFirst().size() - 1;

        // from UP or DOWN
        for (int i = 0; i < map.getFirst().size(); i++) {
            result = Math.max(getResult(deepCopy(map), mutableList(new Beam(UP, 0, i)), maxV, maxH), result);
            result = Math.max(getResult(deepCopy(map), mutableList(new Beam(DOWN, maxV, i)), maxV, maxH), result);
        }
        // from LEFT or RIGHT
        for (int i = 0; i < map.size(); i++) {
            result = Math.max(getResult(deepCopy(map), mutableList(new Beam(LEFT, i, 0)), maxV, maxH), result);
            result = Math.max(getResult(deepCopy(map), mutableList(new Beam(RIGHT, i, maxH)), maxV, maxH), result);
        }


        System.out.println("Result:" + result);
    }

    private List<List<Tile>> deepCopy(List<List<Tile>> map) {
        List<List<Tile>> map2 = new ArrayList<>();
        map.forEach(tiles -> {
            List<Tile> cList = new ArrayList<>();
            map2.add(cList);
            tiles.forEach(tile -> cList.add(new Tile(tile.c)));
        });
        return map2;
    }

    private List<Beam> mutableList(Beam b) {
        List<Beam> l = new ArrayList<>();
        l.add(b);
        return l;
    }

    private long getResult(List<List<Tile>> map, List<Beam> beamList, int maxV, int maxH) {
        long result;

        // cycle
        while (!beamList.isEmpty()) {
            Beam b = beamList.removeFirst();
            beamList.addAll(processBeam(map, b, maxV, maxH));
            System.out.println("BeamC:" + beamList.size());
        }

        // count energize
        result = countEnergize(map);
        return result;
    }
    
    private List<Beam> processBeam(List<List<Tile>> map, Beam b, int maxV, int maxH) {
        if (b.v > maxV || b.v < 0 || b.h > maxH || b.h < 0) {
            return List.of();
        }

        Tile t = map.get(b.v).get(b.h);
        t.battery++;
        if (t.c == '\\') {
            if (b.from.equals(LEFT)) {
                return List.of(new Beam(UP, b));
            } else if (b.from.equals(RIGHT)) {
                return List.of(new Beam(DOWN, b));
            } else if (b.from.equals(UP)) {
                return List.of(new Beam(LEFT, b));
            } else { //DOWN
                return List.of(new Beam(RIGHT, b));
            }
        } else if (t.c == '/') {
            if (b.from.equals(LEFT)) {
                return List.of(new Beam(DOWN, b));
            } else if (b.from.equals(RIGHT)) {
                return List.of(new Beam(UP, b));
            } else if (b.from.equals(UP)) {
                return List.of(new Beam(RIGHT, b));
            } else { //DOWN
                return List.of(new Beam(LEFT, b));
            }
        } else if (t.c == '|') {
            if (!t.canSplit) {
                return List.of();
            }
            t.canSplit = false;
            if (b.from.equals(LEFT) || b.from.equals(RIGHT)) {
                return List.of(new Beam(DOWN, b), new Beam(UP, b));
            } else if (b.from.equals(UP)) {
                return List.of(new Beam(UP, b));
            } else { //DOWN
                return List.of(new Beam(DOWN, b));
            }
        } else if (t.c == '-') {
            if (!t.canSplit) {
                return List.of();
            }
            t.canSplit = false;
            if (b.from.equals(LEFT)) {
                return List.of(new Beam(LEFT, b));
            } else if (b.from.equals(RIGHT)) {
                return List.of(new Beam(RIGHT, b));
            } else { // UP || DOWN
                return List.of(new Beam(LEFT, b), new Beam(RIGHT, b));
            }
        }
        // '.' -> keep direction
        return List.of(new Beam(b.from, b));
    }

    private long countEnergize(List<List<Tile>> map) {
        long res = 0;
        for (List<Tile> tiles : map) {
            for (Tile tile : tiles) {
                if (tile.battery > 0) {
                    res++;
                }
            }
        }
        return res;
    }


}
