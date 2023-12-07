package pl.pacinho.adventofcode2023.challange.day7.tools;

import pl.pacinho.adventofcode2023.challange.day7.model.CardRank;
import pl.pacinho.adventofcode2023.challange.day7.model.CardSetValue;
import pl.pacinho.adventofcode2023.challange.day7.model.Hand;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CardSetValueCalculator {
    public static final Predicate<Collection<List<CardRank>>> _5_SAME_CARDS = values -> values.size() == 1;
    public static final Predicate<Collection<List<CardRank>>> _4_SAME_CARDS = values -> checkAnyListSize(values, 4);
    public static final Predicate<Collection<List<CardRank>>> _HOME = values -> values.size() == 2 && checkAnyListSize(values, 3) && checkAnyListSize(values, 2);
    public static final Predicate<Collection<List<CardRank>>> _3_SAME_CARDS = values -> checkAnyListSize(values, 3);
    public static final Predicate<Collection<List<CardRank>>> _2_PAIRS = values -> getCountOfListSize(values, 2) == 2L;
    public static final Predicate<Collection<List<CardRank>>> _1_PAIRS = values -> getCountOfListSize(values, 2) == 1L;
    public static final Predicate<Collection<List<CardRank>>> _ALL_DISTINCT = values -> values.size() == 5;

    private static long getCountOfListSize(Collection<List<CardRank>> values, int i) {
        return values.stream()
                .filter(list -> list.size() == i)
                .count();
    }

    private static boolean checkAnyListSize(Collection<List<CardRank>> values, int i) {
        return values.stream()
                .anyMatch(list -> list.size() == i);
    }

    public static Long calculateType(Hand hand) {
        Collection<List<CardRank>> values = hand.camelCards().stream()
                .collect(Collectors.groupingBy(CardRank::name))
                .values();

        return (long) CardSetValue.calculate(values);
    }

    public static Long calculateValue(List<CardRank> camelCards) {
        Collection<List<CardRank>> values = camelCards.stream()
                .collect(Collectors.groupingBy(CardRank::name))
                .values();

        return (long) CardSetValue.calculate(values);
    }
}
