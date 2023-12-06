package aoc.orihara.year2023;

import aoc.orihara.Utils;

import java.io.BufferedReader;

public class ProblemDay2 {

    public void runProblem1() throws Exception {
        final int redBag = 12;
        final int greenBag = 13;
        final int blueBag = 14;

        int result = 0;

        final BufferedReader br = Utils.getFile2023("day2.input");

        String line;
        while ((line = br.readLine()) != null) {
            final String[] lineSplit = line.split(":");

            final boolean valid = validPick(lineSplit[1], redBag, greenBag, blueBag);
            if (valid) {
                // get game number
                result += Integer.parseInt(lineSplit[0].trim().split(" ")[1]);
            }
        }

        System.out.println("Result:" + result);
    }

    private boolean validPick(final String full_game, final int redBag, final int greenBag, final int blueBag) {
        int red = 0;
        int green = 0;
        int blue = 0;

        for (final String hand : full_game.trim().split(";")) {
            for (final String balls : hand.trim().split(",")) {
                final String[] ball = balls.trim().split(" ");
                if ("red".equals(ball[1])) {
                    red = Integer.parseInt(ball[0]);
                } else if ("green".equals(ball[1])) {
                    green = Integer.parseInt(ball[0]);
                } else if ("blue".equals(ball[1])) {
                    blue = Integer.parseInt(ball[0]);
                }
            }
            if (red > redBag || green > greenBag || blue > blueBag) {
                return false;
            }
            //System.out.println("aaaaRED: " + red + " Green:" + green + " Blue:" + blue);

        }
        return true;
    }

    public void runProblem2() throws Exception {

        int result = 0;

        final BufferedReader br = Utils.getFile2023("day2.input");

        String line;
        while ((line = br.readLine()) != null) {
            final String[] lineSplit = line.split(":");

            result += gamePower(lineSplit[1]);
        }

        System.out.println("Result:" + result);
    }

    private int gamePower(final String full_game) {
        int redBag = 0;
        int greenBag = 0;
        int blueBag = 0;

        for (final String hand : full_game.trim().split(";")) {
            int red = 0;
            int green = 0;
            int blue = 0;
            for (final String balls : hand.trim().split(",")) {
                final String[] ball = balls.trim().split(" ");
                if ("red".equals(ball[1])) {
                    red = Integer.parseInt(ball[0]);
                } else if ("green".equals(ball[1])) {
                    green = Integer.parseInt(ball[0]);
                } else if ("blue".equals(ball[1])) {
                    blue = Integer.parseInt(ball[0]);
                }
            }
            if (red > redBag) {
                redBag = red;
            }
            if (green > greenBag) {
                greenBag = green;
            }
            if (blue > blueBag) {
                blueBag = blue;
            }
            //System.out.println("aaaaRED: " + red + " Green:" + green + " Blue:" + blue);

        }
        return redBag * greenBag * blueBag;
    }
}
