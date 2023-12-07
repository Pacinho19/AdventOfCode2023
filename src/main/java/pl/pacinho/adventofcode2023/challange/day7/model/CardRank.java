package pl.pacinho.adventofcode2023.challange.day7.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Array;
import java.util.Arrays;

@RequiredArgsConstructor
public enum CardRank {

    _2("2", 2, false),
    _3("3", 3, false),
    _4("4", 4, false),
    _5("5", 5, false),
    _6("6", 6, false),
    _7("7", 7, false),
    _8("8", 8, false),
    _9("9", 9, false),
    T("T", 10, false),
    J("J", 11, true),
    Q("Q", 12, false),
    K("K", 13, false),
    A("A", 14, false);

    private final String label;

    @Getter
    private final int value;
    @Getter
    private final boolean specialCard;

    public static CardRank getCardRank(String _label) {
        return Arrays.stream(CardRank.values())
                .filter(cr -> cr.label.equals(_label))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid card label: " + _label));
    }

    public int getSpecialValue(){
        return specialCard ?  -1 : value;
    }
}
