package aoc.orihara.year2023;

import aoc.orihara.Utils;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.util.List;
import java.util.stream.Stream;

public class ProblemDay6 {
    private static final int boatSpeed = 1;


    public void runProblem1() throws Exception {

        long result = 1;

        final BufferedReader br = Utils.getFile2023("day6.input");

        String lineTime = br.readLine();
        String lineDistanceRecord = br.readLine();

        List<Integer> timeList = Stream.of(lineTime.split(":")[1].split(" "))
                .filter(StringUtils::isNotBlank)
                .map(Integer::parseInt)
                .toList();
        List<Integer> distanceRecordList = Stream.of(lineDistanceRecord.split(":")[1].split(" "))
                .filter(StringUtils::isNotBlank)
                .map(Integer::parseInt)
                .toList();
        for (int i = 0; i < timeList.size(); i++) {
            int time = timeList.get(i);
            int distanceRecord = distanceRecordList.get(i);
            long resultTemp = 0;
            for (int j = 0; j < time + 1; j++) {
                if ((time - j) * (j * boatSpeed) > distanceRecord) {
                    resultTemp++;
                }
            }
            result *= resultTemp;
        }

        System.out.println("Result:" + result);
    }


}
