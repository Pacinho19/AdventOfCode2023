package pl.pacinho.adventofcode2023.challange.day7;

import pl.pacinho.adventofcode2023.CalculateI;
import pl.pacinho.adventofcode2023.challange.day7.model.CardRank;
import pl.pacinho.adventofcode2023.challange.day7.model.Hand;
import pl.pacinho.adventofcode2023.challange.day7.tools.HandParser;
import pl.pacinho.adventofcode2023.utils.FileUtils;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class Day7Part1 implements CalculateI {

    public long calculate(String filePath) {
        List<Hand> list = FileUtils.readTxt(new File(filePath))
                .stream()
                .map(HandParser::parseHand)
                .sorted(Comparator.comparing(this::calculateType,  Comparator.reverseOrder()).thenComparing(this::battle).reversed())
                .toList();

        return IntStream.range(0, list.size())
                .boxed()
                .map(i -> (i + 1) * list.get(i).bidValue())
                .reduce(0L, Long::sum);
    }

    private Integer battle(Hand h1, Hand h2) {
        List<CardRank> cardRanks = h1.camelCards();
        List<CardRank> cardRanks2 = h2.camelCards();

        for (int i = 0; i < cardRanks.size(); i++) {
            CardRank c1 = cardRanks.get(i);
            CardRank c2 = cardRanks2.get(i);

            if (c1.getValue() == c2.getValue())
                continue;

            return Integer.compare(c2.getValue(), c1.getValue());
        }
        return 0;
    }

    private Long calculateType(Hand hand) {
        Collection<List<CardRank>> values = hand.camelCards().stream()
                .collect(Collectors.groupingBy(CardRank::name))
                .values();

        if (values.size() == 1)
            return 7L;
        else if (checkAnyListSize(values, 4))
            return 6L;
        else if (values.size() == 2 && checkAnyListSize(values, 3) && checkAnyListSize(values, 2))
            return 5L;
        else if (checkAnyListSize(values, 3))
            return 4L;
        else if (getCountOfListSize(values, 2) == 2L)
            return 3L;
        else if (getCountOfListSize(values, 2) == 1L)
            return 2L;
        else if (values.size() == 5)
            return 1L;

        return 0L;
    }

    private long getCountOfListSize(Collection<List<CardRank>> values, int i) {
        return values.stream()
                .filter(list -> list.size() == i)
                .count();
    }

    private boolean checkAnyListSize(Collection<List<CardRank>> values, int i) {
        return values.stream()
                .anyMatch(list -> list.size() == i);
    }

    public static void main(String[] args) {
        System.out.println(
                new Day7Part1().calculate("challenges\\day7\\input.txt")
        );
    }
}
