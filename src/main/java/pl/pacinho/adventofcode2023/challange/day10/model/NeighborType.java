package pl.pacinho.adventofcode2023.challange.day10.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum NeighborType {

    NORTH(-1, 0),
    SOUTH(1, 0),
    EAST(0, 1),
    WEST(0, -1);

    @Getter
    private final int yOffset;
    @Getter
    private final int xOffset;
}