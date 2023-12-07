package pl.pacinho.adventofcode2023.challange.day7.tools;

import pl.pacinho.adventofcode2023.challange.day7.model.CardRank;
import pl.pacinho.adventofcode2023.challange.day7.model.Hand;

import java.util.Arrays;
import java.util.List;

public class HandParser {

    public static Hand parseHand(String s) {
        String[] split = s.split(" ");
        String[] cards = split[0].split("");

        return new Hand(parseCards(cards), Long.parseLong(split[1]));
    }

    private static List<CardRank> parseCards(String[] cards) {
        return Arrays.stream(cards)
                .map(CardRank::getCardRank)
                .toList();
    }
}
