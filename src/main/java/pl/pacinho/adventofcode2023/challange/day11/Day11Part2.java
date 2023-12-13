package pl.pacinho.adventofcode2023.challange.day11;

import pl.pacinho.adventofcode2023.CalculateI;
import pl.pacinho.adventofcode2023.challange.day11.model.PositionDto;
import pl.pacinho.adventofcode2023.challange.day11.tools.BFS;
import pl.pacinho.adventofcode2023.model.Pair;
import pl.pacinho.adventofcode2023.utils.FileUtils;
import pl.pacinho.adventofcode2023.utils.NeighborsUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day11Part2 implements CalculateI {
    private final int extendedValue;

    public Day11Part2(int extendedValue) {
        this.extendedValue = extendedValue;
    }

    public static void main(String[] args) {
        System.out.println(
                new Day11Part2(1_000_000).calculate("challenges\\day11\\input.txt")
        );
    }

    @Override
    public long calculate(String filePath) {
        return new Day11Part1(extendedValue).calculate(filePath);
    }
}
