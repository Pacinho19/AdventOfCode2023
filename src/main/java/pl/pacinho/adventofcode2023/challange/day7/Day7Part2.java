package pl.pacinho.adventofcode2023.challange.day7;

import pl.pacinho.adventofcode2023.CalculateI;
import pl.pacinho.adventofcode2023.challange.day7.model.CardRank;
import pl.pacinho.adventofcode2023.challange.day7.model.Hand;
import pl.pacinho.adventofcode2023.challange.day7.tools.Battle;
import pl.pacinho.adventofcode2023.challange.day7.tools.CardSetValueCalculator;
import pl.pacinho.adventofcode2023.challange.day7.tools.HandParser;
import pl.pacinho.adventofcode2023.utils.FileUtils;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

public class Day7Part2 implements CalculateI {


    public long calculate(String filePath) {
        List<Hand> list = FileUtils.readTxt(new File(filePath))
                .stream()
                .map(HandParser::parseHand)
                .sorted(Comparator.comparing(this::calculateType, Comparator.reverseOrder()).thenComparing((h1,h2) -> Battle.goBattle(h1, h2, CardRank::getSpecialValue)).reversed())
                .toList();

        return IntStream.range(0, list.size())
                .boxed()
                .map(i -> (i + 1) * list.get(i).bidValue())
                .reduce(0L, Long::sum);
    }

    private Long calculateType(Hand hand) {
        Long aLong = CardSetValueCalculator.calculateValue(hand.camelCards());
        if (aLong == 7L || !hasJoker(hand.camelCards()))
            return aLong;

        return Arrays.stream(CardRank.values())
                .map(cr -> CardSetValueCalculator.calculateValue(replaceJoker(hand.camelCards(), cr)))
                .max(Long::compareTo)
                .orElse(0L);
    }

    private List<CardRank> replaceJoker(List<CardRank> cardRanks, CardRank cr) {
        return cardRanks.stream()
                .map(cardRank -> cardRank != CardRank.J ? cardRank : cr)
                .toList();
    }

    private boolean hasJoker(List<CardRank> cardRanks) {
        return cardRanks.stream()
                .anyMatch(cr -> cr == CardRank.J);
    }

    public static void main(String[] args) {
        System.out.println(
                new Day7Part2().calculate("challenges\\day7\\input.txt")
        );
    }
}
