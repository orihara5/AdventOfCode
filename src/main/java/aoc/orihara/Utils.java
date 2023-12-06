package aoc.orihara;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Utils {
    private final static String TO_RESOURCES = "/../../git/AdventOfCode/src/main/resources/2023/";

    public static BufferedReader getFile2023(String fileName) throws Exception {

        final File file = new File(System.getProperty("user.dir") + TO_RESOURCES + fileName);
        return new BufferedReader(new FileReader(file));
    }
}
