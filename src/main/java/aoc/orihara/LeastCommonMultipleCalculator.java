package aoc.orihara;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LeastCommonMultipleCalculator {

    public Long calculate(List<Long> numbers) {
        List<Long> dividers = getDividers(numbers);

        return dividers.stream().reduce((aLong, aLong2) -> aLong * aLong2).orElse(0L);
    }

    public List<Long> getDividers(List<Long> numbers) {
        List<Long> dividers = new ArrayList<>();

        List<Long> currentNumberList = numbers;
        Long maxVal = currentNumberList.stream().reduce(Math::max).orElse(1L);
        List<Long> nextNumberList;
        Iterator<Long> listOfPrimes = getListOfPrime(maxVal).iterator();
        Long currentPrime = listOfPrimes.next();
        while (allNotOnes(currentNumberList)) {
            nextNumberList = new ArrayList<>();

            for (Long number : currentNumberList) {
                if (number % currentPrime == 0) {
                    nextNumberList.add(number / currentPrime);
                } else {
                    nextNumberList.add(number);
                }
            }
            if (currentNumberList.equals(nextNumberList)) {
                currentPrime = listOfPrimes.next();
//                System.out.println("currentNumberList: " + currentNumberList);
            } else {
                dividers.add(currentPrime);
//                System.out.println("dividers: " + currentPrime);
            }
            currentNumberList = nextNumberList;
        }
        return dividers;
    }

    private boolean allNotOnes(List<Long> currentNumberList) {
        return currentNumberList.stream().anyMatch(aLong -> aLong != 1);
    }

    public List<Long> getListOfPrime(Long maxSearch) {
        List<Long> result = new ArrayList<>();
        result.add(2L);
        result.add(3L);
        result.add(5L);

        for (long i = 5; i < maxSearch; i += 2) {
            final Long val = i;
            if (!result.stream().anyMatch(aLong -> val % aLong == 0)) {
                result.add(val);
            }
        }

        return result;
    }
}
