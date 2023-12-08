package aoc.orihara.year2023;

import aoc.orihara.Utils;

import java.io.BufferedReader;
import java.util.*;

public class ProblemDay7part2 {

    class Hand implements Comparable<Hand> {
        List<Character> originalHand;
        Long bid;
        HandType type;

        public Hand(String original, Long bid) {
            this.originalHand = original.chars().mapToObj(e -> (char) e).toList();
            this.bid = bid;
            giveClassification(this);
        }

        @Override
        public int compareTo(Hand o) {
            return compare(this, o);
        }
    }


    public enum HandType {
        Five_of_a_kind(10),
        Four_of_a_kind(9),
        Full_house(8),
        Three_of_a_kind(7),
        Two_pair(6),
        One_pair(5),
        High_card(4);

        final Integer weight;

        HandType(int weight) {
            this.weight = weight;
        }
    }

    public enum CardPoints {
        A_A('A', 14),
        A_K('K', 13),
        A_Q('Q', 12),
        A_J('J', 1), // weak
        A_T('T', 10),
        A_9('9', 9),
        A_8('8', 8),
        A_7('7', 7),
        A_6('6', 6),
        A_5('5', 5),
        A_4('4', 4),
        A_3('3', 3),
        A_2('2', 2);
        //A, K, Q, J, T, 9, 8, 7, 6, 5, 4, 3, or 2

        final Integer points;
        final Character value;

        CardPoints(Character value, int points) {
            this.value = value;
            this.points = points;
        }

        final static Map<Character, Integer> lookup;

        static Integer getValue(Character c) {
            return lookup.get(c);
        }

        static {
            lookup = new HashMap<>();
            Arrays.stream(CardPoints.values())
                    .forEach(cardPoints -> {
                        lookup.put(cardPoints.value, cardPoints.points);
                    });
        }
    }

    public void giveClassification(Hand hand) {

        long countJ = hand.originalHand.stream()
                .filter(character -> character.equals('J'))
                .count();
        long countNonDuplicated = hand.originalHand.stream()
                .distinct()
                .count();
        if (countNonDuplicated == 1 || countJ == 4) {
            hand.type = HandType.Five_of_a_kind;
            return;
        }
        if (countNonDuplicated == 4) {
            hand.type = countJ == 0 ? HandType.One_pair : HandType.Three_of_a_kind;
            return;
        }
        if (countNonDuplicated == 5) {
            hand.type = countJ == 0 ? HandType.High_card : HandType.One_pair;
            return;
        }
        //--------
        if (countNonDuplicated == 3) {
            if (countJ == 0) {
                List<Character> different = hand.originalHand.stream().distinct().toList();
                for (Character value : different) {
                    if (hand.originalHand.stream().filter(character -> character.equals(value)).count() == 3) {
                        hand.type = HandType.Three_of_a_kind;
                        return;
                    }
                }
                hand.type = HandType.Two_pair;
                return;
            } else if (countJ == 3 || countJ == 2) {
                hand.type = HandType.Four_of_a_kind;
                return;
            } else {
                List<Character> different = hand.originalHand.stream().distinct().toList();
                for (Character value : different) {
                    if (hand.originalHand.stream().filter(character -> character.equals(value)).count() == 3) {
                        hand.type = HandType.Four_of_a_kind;
                        return;
                    }
                }
                hand.type = HandType.Full_house;
                return;
            }
        }
        //-------- countNonDuplicated == 2
        if (countJ == 0) {
            List<Character> different = hand.originalHand.stream().distinct().toList();
            Long count = hand.originalHand.stream().filter(character -> character.equals(different.getFirst())).count();
            if (count == 4 || count == 1) {
                hand.type = HandType.Four_of_a_kind;
                return;
            }
            hand.type = HandType.Full_house;
            return;
        }

        hand.type = HandType.Five_of_a_kind;
    }

    public int compare(Hand a, Hand b) {
        int compare = a.type.weight.compareTo(b.type.weight);
        if (compare != 0) {
            return compare;
        }
        for (int i = 0; i < a.originalHand.size(); i++) {

            compare = compare(a.originalHand.get(i), b.originalHand.get(i));
            if (compare != 0) {
                return compare;
            }
        }
        System.out.println("this should be impossible");
        return 0;
    }

    public int compare(Character a, Character b) {
        return CardPoints.getValue(a).compareTo(CardPoints.getValue(b));
    }

    public void runProblem() throws Exception {


        final BufferedReader br = Utils.getFile2023("day7.input");

        List<Hand> bag = new ArrayList<>();

        String line;
        while ((line = br.readLine()) != null) {
            String[] s = line.split(" ");
            bag.add(new Hand(s[0], Long.parseLong(s[1])));
        }
        bag = bag.stream().sorted().toList();

        long result = 0;
        for (int i = 0; i < bag.size(); i++) {
            result += (i + 1) * bag.get(i).bid;
        }

        System.out.println("Result:" + result);
        // 253608012 H
        // 253253225
    }


}
