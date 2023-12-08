package pl.pacinho.adventofcode2023.challange.day8.model;

import lombok.RequiredArgsConstructor;
import pl.pacinho.adventofcode2023.model.Pair;

import java.util.Arrays;
import java.util.function.Function;

@RequiredArgsConstructor
public enum Side {

    L(Pair::key),
    R(Pair::value);

    private final Function<Pair<String, String>, String> func;

    public static Side getSide(String sign) {
        return Arrays.stream(Side.values())
                .filter(side -> side.name().equals(sign))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid side sign: " + sign));
    }

    public String applyFunc(Pair<String, String> pair) {
        return func.apply(pair);
    }
}
