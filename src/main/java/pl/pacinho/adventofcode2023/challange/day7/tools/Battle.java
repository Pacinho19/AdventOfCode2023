package pl.pacinho.adventofcode2023.challange.day7.tools;

import pl.pacinho.adventofcode2023.challange.day7.model.CardRank;
import pl.pacinho.adventofcode2023.challange.day7.model.Hand;

import java.util.List;
import java.util.function.Function;

public class Battle {
    public static Integer goBattle(Hand h1, Hand h2, Function<CardRank, Integer> func) {

        List<CardRank> cardRanks = h1.camelCards();
        List<CardRank> cardRanks2 = h2.camelCards();

        for (int i = 0; i < cardRanks.size(); i++) {
            CardRank c1 = cardRanks.get(i);
            CardRank c2 = cardRanks2.get(i);

            if (func.apply(c1) == func.apply(c2))
                continue;

            return Integer.compare(func.apply(c2), func.apply(c1));
        }
        return 0;
    }

}
