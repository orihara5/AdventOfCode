package aoc.orihara.year2023;

import aoc.orihara.Utils;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProblemDay15part2 {

    class Lens {
        String lens;
        long focal;

        public Lens(String lens, long focal) {
            this.lens = lens;
            this.focal = focal;
        }
    }

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
        //  hash        lens   focal
        Map<Long, List<Lens>> map = new HashMap<>();
        for (String p : puzzle2) {
            long hash;
            long focal = 0;
            if (p.indexOf('-') != -1) {
                String[] ss = p.split("-");
                hash = calculateHash(ss[0]);
                List<Lens> t = map.get(hash);
                if (t != null) {
                    Lens lens = null;
                    for (Lens l : t) {
                        if (l.lens.equals(ss[0])) {
                            lens = l;
                            break;
                        }
                    }
                    t.remove(lens);
                }
            } else {
                String[] ss = p.split("=");
                hash = calculateHash(ss[0]);
                focal = Long.parseLong(ss[1]);
                List<Lens> t = map.computeIfAbsent(hash, k -> new ArrayList<>());
                for (Lens l : t) {
                    if (l.lens.equals(ss[0])) {
                        l.focal = focal;
                        focal = -1;
                        break;
                    }
                }
                if (focal != -1) {
                    t.add(new Lens(ss[0], focal));
                }
            }
        }
        for (Map.Entry<Long, List<Lens>> v : map.entrySet()) {
            List<Lens> value = v.getValue();
            for (int i = 0; i < value.size(); i++) {
                Lens l = value.get(i);
                result += (1 + v.getKey()) * (1 + i) * l.focal;

            }
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
