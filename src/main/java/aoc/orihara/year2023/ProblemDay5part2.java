package aoc.orihara.year2023;

import aoc.orihara.Utils;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProblemDay5part2 {
    private final static String TO_RESOURCES = "/../../git/AdventOfCode/src/main/resources/";

    class Something {
        String target;
        List<Long> sourceIndex = new ArrayList<>();
        List<Long> targetIndex = new ArrayList<>();
        List<Long> range = new ArrayList<>();
    }

    public void runProblem2() throws Exception {
        long startTime = System.currentTimeMillis();

        long result = 0;

        final BufferedReader br = Utils.getFile2023("day5.input");

        String line;
        List<Long> seeds = new ArrayList<>();

        boolean firstBlock = true;
        List<String> block = new ArrayList<>();
        Map<String, Something> somethingList = new HashMap<>();
        while ((line = br.readLine()) != null) {
//            System.out.println(line);
            if (StringUtils.isBlank(line)) {
                if (firstBlock) {
                    seeds = processFirstBlock(block);
                    System.out.println(seeds);
                    firstBlock = false;
                } else {
                    Map.Entry<String, Something> entry = processSomething(block);
                    somethingList.put(entry.getKey(), entry.getValue());
                }
                block = new ArrayList<>();
            } else {
                block.add(line);
            }
        }
        // process last line
        if (!block.isEmpty()) {
            Map.Entry<String, Something> entry = processSomething(block);
            somethingList.put(entry.getKey(), entry.getValue());
        }

        // calculate everything
        result = calculateLocation(seeds, somethingList);

        System.out.println("Result: " + result);
        System.out.println("Time: " + (System.currentTimeMillis() - startTime));
    }


    private long calculateLocation(List<Long> seeds, Map<String, Something> somethingList) {
        String previousTarget = "seed";
        long result = Long.MAX_VALUE;
        Long count = 0L;
        Long total = 0L;
        for (int i = 0; i + 1 < seeds.size(); i += 2) {
            System.out.println("Bag " + i / 2 + " out of " + seeds.size() / 2);
            Long max = seeds.get(i) + seeds.get(i + 1);
            System.out.println("Start " + seeds.get(i) + " Length " + seeds.get(i + 1) + " Max " + max);
            total += seeds.get(i + 1);

            for (Long j = seeds.get(i); j < max; j++) {
                previousTarget = "seed";
                Long targetIndex = j;
                count++;
//                if ((count % 1000000) == 0) {
//                    System.out.println("Count " + count);
//                }
                while (!Objects.equals(previousTarget, "location")) {
//                System.out.println("previousTarget: " + previousTarget);
                    Something some = somethingList.get(previousTarget);
                    if (some == null) {
                        break;
                    }
                    previousTarget = some.target;
                    targetIndex = getTargetMap(targetIndex, some);
                }

                if (previousTarget.equals("location")) {
                    result = Math.min(targetIndex, result);
                    if (targetIndex == result) {
                        System.out.println("seed updated");
                    }
                } else {
                    System.out.println("seed ignored");
                } // 37806487 // 37806486

            }
        }
        System.out.println("Total: " + total);
        return result;
    }

    private static Long getTargetMap(Long seed, Something some) {
        for (int i = 0; i < some.sourceIndex.size(); i++) {
            if (seed >= some.sourceIndex.get(i) && seed < (some.sourceIndex.get(i) + some.range.get(i))) {
//                System.out.println("getTargetMap::::::: ");
                return some.targetIndex.get(i) - some.sourceIndex.get(i) + seed;
            }
        }
//        System.out.println("getTargetMap: ");
        return seed;
    }

    private Map.Entry<String, Something> processSomething(List<String> block) {

        final String[] lineSplit = block.get(0).replace(" map:", "").split("-to-");

        Something some = new Something();
//        System.out.println(block);
        // first already processed
        for (int i = 1; i < block.size(); i++) {
            List<Long> values = Stream.of(block.get(i).split(" "))
                    .filter(Objects::nonNull)
                    .map(Long::parseLong)
                    .toList();
            some.targetIndex.add(values.get(0));
            some.sourceIndex.add(values.get(1));
//            some.targetIndex.add(values.get(1));
//            some.sourceIndex.add(values.get(0));
            some.range.add(values.get(2));
        }
        some.target = lineSplit[1];
        return new AbstractMap.SimpleEntry<>(lineSplit[0], some);

    }

    private List<Long> processFirstBlock(List<String> block) {
        final String[] lineSplit = block.get(0).split(":");
        return Stream.of(lineSplit[1].trim().split(" "))
                .map(Long::parseLong)
                .collect(Collectors.toList());


    }

}
