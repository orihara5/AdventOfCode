package aoc.orihara.year2023;

import aoc.orihara.Utils;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.util.stream.Stream;

public class ProblemDay6part2 {
    private static final int boatSpeed = 1;


    public void runProblem() throws Exception {

        long result = 0;

        final BufferedReader br = Utils.getFile2023("day6.input");

        String lineTime = br.readLine();
        String lineDistanceRecord = br.readLine();

        Long time = Stream.of(lineTime.split(":")[1].split(" "))
                .filter(StringUtils::isNotBlank)
                .reduce(String::concat)
                .map(Long::parseLong)
                .orElse(0L);
        Long distanceRecord = Stream.of(lineDistanceRecord.split(":")[1].split(" "))
                .filter(StringUtils::isNotBlank)
                .reduce(String::concat)
                .map(Long::parseLong)
                .orElse(0L);

        for (int j = 0; j < time + 1; j++) {
            if ((time - j) * (j * boatSpeed) > distanceRecord) {
                result++;
            }
        }

        System.out.println("Result:" + result);
    }


}
