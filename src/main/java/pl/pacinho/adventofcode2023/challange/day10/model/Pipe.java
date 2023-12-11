package pl.pacinho.adventofcode2023.challange.day10.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
public enum Pipe {

    VERTICAL("|", List.of(
            new Neighbor(NeighborType.NORTH, List.of("7", "F", "|"))
            , new Neighbor(NeighborType.SOUTH, List.of("L", "J", "|"))
    )),
    HORIZONTAL("-", List.of(
            new Neighbor(NeighborType.EAST, List.of("J", "7", "-"))
            , new Neighbor(NeighborType.WEST, List.of("L", "F", "-"))
    )),
    CURVE_N_E("L", List.of(
            new Neighbor(NeighborType.NORTH, List.of("7", "F", "|"))
            , new Neighbor(NeighborType.EAST, List.of("J", "7", "-"))
    )),
    CURVE_W_N("J", List.of(
            new Neighbor(NeighborType.NORTH, List.of("7", "F", "|"))
            , new Neighbor(NeighborType.WEST, List.of("L", "F", "-"))
    )),
    CURVE_W_S("7", List.of(
            new Neighbor(NeighborType.SOUTH, List.of("|", "L", "J"))
            , new Neighbor(NeighborType.WEST, List.of("-", "F", "L"))
    )),
    CURVE_E_S("F", List.of(
            new Neighbor(NeighborType.SOUTH, List.of("|", "L", "J"))
            , new Neighbor(NeighborType.EAST, List.of("J", "7", "-"))
    )),
    NONE(".", Collections.emptyList()),
    START("S", Collections.emptyList());

    @Getter
    private final String sign;
    @Getter
    private final List<Neighbor> neighbors;

    public static Pipe findBySign(String sign) {
        return Arrays.stream(Pipe.values())
                .filter(pipe -> pipe.sign.equals(sign))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid sign:" + sign));
    }

}