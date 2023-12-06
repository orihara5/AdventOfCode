package aoc.orihara.year2023;

import aoc.orihara.Utils;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProblemDay5 {

    class Something {
        String target;
        List<Long> sourceIndex = new ArrayList<>();
        List<Long> targetIndex = new ArrayList<>();
        List<Long> range = new ArrayList<>();
    }

    public void runProblem1() throws Exception {

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

        System.out.println("Result:" + result);
    }


    private long calculateLocation(List<Long> seeds, Map<String, Something> somethingList) {
        String previousTarget = "seed";
        long result = Long.MAX_VALUE;
        for (Long seed : seeds) {
            previousTarget = "seed";
            Long targetIndex = seed;
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
            } else {
                System.out.println("seed ignored");
            } // 16030044 // 1181555926


        }
        return result;
    }

    private static Long getTargetMap(Long seed, Something some) {
        for (int i = 0; i < some.sourceIndex.size(); i++) {
            if (seed >= some.sourceIndex.get(i) && seed <= (some.sourceIndex.get(i) + some.range.get(i))) {
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
