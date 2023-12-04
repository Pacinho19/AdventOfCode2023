package pl.pacinho.adventofcode2023.challange.day3;

import pl.pacinho.adventofcode2023.CalculateI;
import pl.pacinho.adventofcode2023.model.NeighborDto;
import pl.pacinho.adventofcode2023.model.Pair;
import pl.pacinho.adventofcode2023.utils.DigitUtils;
import pl.pacinho.adventofcode2023.utils.FileUtils;
import pl.pacinho.adventofcode2023.utils.NeighborsUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Day3Part2 implements CalculateI {
    private static final String GEAR_SIGN = "*";
    private List<Long> numbers;
    private String[][] engine;
    private NeighborsUtils neighborsUtils;
    private List<String> checked;

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
                if (!isGear(i, j))
                    continue;

                long ratio = checkPart(i, j);
                numbers.add(ratio);
            }
        }

        return numbers.stream()
                .reduce(0L, Long::sum);
    }

    private long checkPart(int i, int j) {
        List<Long> list = neighborsUtils.getNeighbors(i, j)
                .stream()
                .filter(ngh -> DigitUtils.isDigit(engine[ngh.y()][ngh.x()]))
                .map(ngh -> extractNumber(ngh.y(), ngh.x()))
                .filter(value -> value>0)
                .toList();

        if (list.size() != 2)
            return 0L;

        return list.stream()
                .reduce(1L, (l1, l2) -> l1 * l2);
    }

    private boolean isGear(int i, int j) {
        return engine[i][j].equals(GEAR_SIGN);
    }

    private long extractNumber(int i, int j) {
        if (checked.contains(i + "," + j))
            return 0L;

        String numberS = engine[i][j];
        if (!DigitUtils.isDigit(numberS))
            return 0L;

        checked.add(i + "," + j);

        Pair<Integer, Integer> prevOffset = new Pair<>(0, -1);
        Pair<Integer, Integer> nextOffset = new Pair<>(0, 1);
        String prevDigit = nextDigit(i, j, prevOffset);
        String nextDigit = nextDigit(i, j, nextOffset);
        while (prevDigit != null || nextDigit != null) {
            numberS = (prevDigit != null ? prevDigit : "") + numberS + (nextDigit != null ? nextDigit : "");
            prevOffset = new Pair<>(0, prevOffset.value() - 1);
            nextOffset = new Pair<>(0, nextOffset.value() + 1);
            prevDigit = prevDigit != null ? nextDigit(i, j, prevOffset) : null;
            nextDigit = nextDigit != null ? nextDigit(i, j, nextOffset) : null;
        }

        return Long.parseLong(numberS);
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

    public static void main(String[] args) {
        System.out.println(
                new Day3Part2().calculate("challenges\\day3\\input.txt")
        );
    }
}