package pl.pacinho.adventofcode2023.challange.day3;

import pl.pacinho.adventofcode2023.CalculateI;
import pl.pacinho.adventofcode2023.model.NeighborDto;
import pl.pacinho.adventofcode2023.model.Pair;
import pl.pacinho.adventofcode2023.utils.DigitUtils;
import pl.pacinho.adventofcode2023.utils.FileUtils;
import pl.pacinho.adventofcode2023.utils.NeighborsUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Day3Part1 implements CalculateI {
    private final List<String> ILLEGAL_SIGNS = List.of(".");
    private List<Long> numbers;
    private List<String> checked;
    private String[][] engine;

    private NeighborsUtils neighborsUtils;

    @Override
    public long calculate(String filePath) {

        numbers = new ArrayList<>();
        checked = new ArrayList<>();

        engine = FileUtils.readTxt(new File(filePath))
                .stream()
                .map(s -> s.split(""))
                .toArray(size -> new String[size][1]);

        neighborsUtils = new NeighborsUtils(engine[0].length, engine.length);
        for (int i = 0; i < engine.length; i++) {
            for (int j = 0; j < engine.length; j++) {
                checkEngine(i, j);
            }
        }

        return numbers.stream()
                .reduce(0L, Long::sum);
    }

    private void checkEngine(int i, int j) {
        if (checked.contains(i + "," + j))
            return;

        checked.add(i + "," + j);

        String value = engine[i][j];
        boolean isDigit = DigitUtils.isDigit(value);
        if (!isDigit)
            return;

        boolean isNeighborSpecialSign = checkNeighborsIsSpecialSign(i, j);
        if (isNeighborSpecialSign)
            extractNumber(i, j);
    }

    private void extractNumber(int i, int j) {
        String numberS = engine[i][j];

        Pair<Integer, Integer> prevOffset = new Pair<>(0, -1);
        Pair<Integer, Integer> nextOffset = new Pair<>(0, 1);
        String prevDigit = nextDigit(i, j, prevOffset);
        String nextDigit = nextDigit(i, j, nextOffset);
        while (prevDigit != null || nextDigit != null) {
            numberS = (prevDigit != null ? prevDigit : "") + numberS + (nextDigit != null ? nextDigit : "");
            prevOffset =  new Pair<>(0, prevOffset.value() - 1);
            nextOffset =  new Pair<>(0, nextOffset.value() + 1);
            prevDigit = prevDigit!=null ? nextDigit(i, j,prevOffset) : null;
            nextDigit = nextDigit!=null ? nextDigit(i, j, nextOffset): null;
        }

        numbers.add(Long.valueOf(numberS));
    }

    private String nextDigit(int i, int j, Pair<Integer, Integer> offset) {
        if (!neighborsUtils.isValidNeighbor(i, j, offset))
            return null;

        checked.add((i + offset.key()) + "," + (j + offset.value()));

        String value = engine[i + offset.key()][j + offset.value()];
        if (DigitUtils.isDigit(value))
            return value;

        return null;
    }

    private boolean checkNeighborsIsSpecialSign(int i, int j) {
        List<NeighborDto> neighbors = neighborsUtils.getNeighbors(i, j);

        return neighbors.stream()
                .anyMatch(n -> {
                    String value = engine[n.y()][n.x()];
                    return !DigitUtils.isDigit(value) && !ILLEGAL_SIGNS.contains(value);
                });
    }

    public static void main(String[] args) {
        System.out.println(
                new Day3Part1().calculate("challenges\\day3\\input.txt")
        );
    }
}