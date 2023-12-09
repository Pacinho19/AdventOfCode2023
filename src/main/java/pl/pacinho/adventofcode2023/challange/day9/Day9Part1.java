package pl.pacinho.adventofcode2023.challange.day9;

import pl.pacinho.adventofcode2023.CalculateI;
import pl.pacinho.adventofcode2023.utils.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Day9Part1 implements CalculateI {
    @Override
    public long calculate(String filePath) {
        return FileUtils.readTxt(new File(filePath))
                .stream()
                .map(this::parseSignalHistory)
                .reduce(0L, Long::sum);
    }
    private Long parseSignalHistory(String s) {
        Long[] arr = parseNumbersArray(s.split(" "));
        List<Long[]> out = new ArrayList<>();
        out.add(arr);

        while (!isAllValuesZero(out)) {
            Long[] lastRecord = getLastRecord(out);
            Long[] newArr = IntStream.range(0, lastRecord.length - 1)
                    .boxed()
                    .map(i -> lastRecord[i + 1] - lastRecord[i])
                    .toArray(Long[]::new);

            out.add(newArr);
        }
        return doExtrapolate(out);
    }

    private Long doExtrapolate(List<Long[]> out) {
        long lastNumber = 0;
        for (int i = out.size() - 2; i >= 0; i--) {
            Long[] longs = out.get(i);
            lastNumber = longs[longs.length-1]+lastNumber;
        }
        return lastNumber;
    }

    private boolean isAllValuesZero(List<Long[]> out) {
        Long[] lastRecord = getLastRecord(out);
        return Arrays.stream(lastRecord)
                .allMatch(l -> l == 0L);
    }

    private Long[] getLastRecord(List<Long[]> out) {
        return out.get(out.size() - 1);
    }

    private Long[] parseNumbersArray(String[] s) {
        return Arrays.stream(s)
                .map(Long::parseLong)
                .toArray(Long[]::new);
    }

    public static void main(String[] args) {
        System.out.println(
                new Day9Part1().calculate("challenges\\day9\\input.txt")
        );
    }
}