package aoc.orihara.year2023;

import aoc.orihara.Utils;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class ProblemDay4 {

    public void runProblem1() throws Exception {

        double result = 0;

        final BufferedReader br = Utils.getFile2023("day4.input");

        String line;
        while ((line = br.readLine()) != null) {
            final String[] lineSplit = line.split(":");

            result += getCardPoints(lineSplit[1]);

        }

        System.out.println("Result: " + result);
    }

    private double getCardPoints(final String card) {
        final String[] cardSplit = card.trim().split("\\|"); // fuck this special character
        //System.out.println("card: " + card);
        //System.out.println("card1: " + cardSplit[0]);
        //System.out.println("card2: " + cardSplit[1]);

        final ArrayList<Integer> winningNumbersCard = new ArrayList<>(
                Arrays.stream(cardSplit[0].trim().split(" "))
                        .map(String::trim)
                        .filter(StringUtils::isNotBlank)
                        .map(Integer::parseInt)
                        .toList());
        //System.out.println("winningNumbersCard: " + winningNumbersCard);

        final ArrayList<Integer> numbersCard = new ArrayList<>(
                Arrays.stream(cardSplit[1].trim().split(" "))
                        .map(String::trim)
                        .filter(StringUtils::isNotBlank)
                        .map(Integer::parseInt)
                        .toList());
        //System.out.println("numbersCard: " + numbersCard);

        final long winningNumbersCount = winningNumbersCard.stream()
                .filter(numbersCard::contains)
                .count();
        if (winningNumbersCount == 0) {
            return 0;
        }
        final double result = Math.pow(new Double(2), new Double(winningNumbersCount - 1));
        System.out.println("resultTemp: " + result);
        return result;
        // 52896 // 26426
    }

    public void runProblem2() throws Exception {

        int result = 0;

        final BufferedReader br = Utils.getFile2023("day4.input");

        final List<Integer> numberOfExtraCards = new ArrayList<>();
        for (int i = 0; i < 250; i++) { // to lazy to read the file 2 times OR catch OutOfBoundsException
            numberOfExtraCards.add(0);
        }
        String line;
        int currentIndex = 0;
        int cardNumber = 0; // also total of original cards
        while ((line = br.readLine()) != null) {
            final String[] lineSplit = line.split(":");
//            System.out.println(lineSplit[0]);
            cardNumber = Integer.parseInt(
                    Stream.of(lineSplit[0].trim().split(" "))
                            .filter(StringUtils::isNotBlank)
                            .toList()
                            .get(1));
            numberOfExtraCards.set(currentIndex, numberOfExtraCards.get(currentIndex) + 1);

            long cardsWon = getCardWinningNumbers(lineSplit[1]);
            System.out.println(cardsWon);
            for (int i = currentIndex + 1; i <= currentIndex + cardsWon; i++) {
                System.out.println("aaaaaa");
                numberOfExtraCards.set(i, numberOfExtraCards.get(i) + numberOfExtraCards.get(currentIndex));
            }
            System.out.println(currentIndex);

            System.out.println(numberOfExtraCards);
            currentIndex++;
        }
        result = numberOfExtraCards.stream().reduce(Integer::sum).orElse(0);

        System.out.println("Result: " + result);
        // 6228191 High // 6227972
    }

    private long getCardWinningNumbers(String card) {
        final String[] cardSplit = card.trim().split("\\|"); // fuck this special character

        final ArrayList<Integer> winningNumbersCard = new ArrayList<>(
                Arrays.stream(cardSplit[0].trim().split(" "))
                        .map(String::trim)
                        .filter(StringUtils::isNotBlank)
                        .map(Integer::parseInt)
                        .toList());

        final ArrayList<Integer> numbersCard = new ArrayList<>(
                Arrays.stream(cardSplit[1].trim().split(" "))
                        .map(String::trim)
                        .filter(StringUtils::isNotBlank)
                        .map(Integer::parseInt)
                        .toList());

        return winningNumbersCard.stream()
                .filter(numbersCard::contains)
                .count();
    }
}
