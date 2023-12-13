package pl.pacinho.adventofcode2023.utils;

import pl.pacinho.adventofcode2023.challange.day11.model.PositionDto;
import pl.pacinho.adventofcode2023.model.NeighborDto;
import pl.pacinho.adventofcode2023.model.Pair;

import java.util.List;
import java.util.Objects;

public class NeighborsUtils {

    private static final List<Pair<Integer, Integer>> NEIGHBORS = List.of(
            new Pair<>(-1, -1),
            new Pair<>(-1, 0),
            new Pair<>(-1, 1),
            new Pair<>(0, -1),
            //SELF
            new Pair<>(0, 1),
            new Pair<>(1, -1),
            new Pair<>(1, 0),
            new Pair<>(1, 1)
    );

    private static final List<Pair<Integer, Integer>> NEIGHBORS_SIMPLE = List.of(
            new Pair<>(-1, 0),
            new Pair<>(0, -1),
            //SELF
            new Pair<>(0, 1),
            new Pair<>(1, 0)
    );

    private final int width;
    private final int height;

    public NeighborsUtils(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public List<NeighborDto> getNeighbors(int y, int x) {
        return NEIGHBORS.stream()
                .map(pair -> parseNeighborDto(pair, y, x))
                .filter(Objects::nonNull)
                .toList();

    }

    public List<NeighborDto> getNeighborsSimple(int y, int x) {
        return NEIGHBORS_SIMPLE.stream()
                .map(pair -> parseNeighborDto(pair, y, x))
                .filter(Objects::nonNull)
                .toList();

    }

    private NeighborDto parseNeighborDto(Pair<Integer, Integer> offset, int y, int x) {
        int newY = y + offset.key();
        int newX = x + offset.value();

        if (!isValidNeighbor(y, x, offset))
            return null;

        return new NeighborDto(newY, newX);
    }

    public boolean isValidNeighbor(int y, int x, Pair<Integer, Integer> offset) {
        int newY = y + offset.key();
        int newX = x + offset.value();

        if (newY < 0 || newY >= height)
            return false;

        if (newX < 0 || newX >= width)
            return false;

        return true;
    }
}
