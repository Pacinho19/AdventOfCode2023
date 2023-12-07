package pl.pacinho.adventofcode2023.challange.day7.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.pacinho.adventofcode2023.challange.day7.tools.CardSetValueCalculator;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

@RequiredArgsConstructor
public enum CardSetValue {

    _7(7, CardSetValueCalculator._5_SAME_CARDS),
    _6(6, CardSetValueCalculator._4_SAME_CARDS),
    _5(5, CardSetValueCalculator._HOME),
    _4(4, CardSetValueCalculator._3_SAME_CARDS),
    _3(3, CardSetValueCalculator._2_PAIRS),
    _2(2, CardSetValueCalculator._1_PAIRS),
    _1(1, CardSetValueCalculator._ALL_DISTINCT);

    @Getter
    private final int value;
    private final Predicate<Collection<List<CardRank>>> qualifier;

    public static int calculate(Collection<List<CardRank>> values) {
        return Arrays.stream(CardSetValue.values())
                .filter(cs -> cs.qualifier.test(values))
                .findFirst()
                .map(CardSetValue::getValue)
                .orElse(0);
    }
}
