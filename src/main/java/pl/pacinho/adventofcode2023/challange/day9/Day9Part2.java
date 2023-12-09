package pl.pacinho.adventofcode2023.challange.day9;

import pl.pacinho.adventofcode2023.CalculateI;
import pl.pacinho.adventofcode2023.challange.day9.tools.HistoryTools;
import pl.pacinho.adventofcode2023.utils.FileUtils;

import java.io.File;
import java.util.List;

public class Day9Part2 implements CalculateI {
    @Override
    public long calculate(String filePath) {
        return FileUtils.readTxt(new File(filePath))
                .stream()
                .map(HistoryTools::parseSignalHistory)
                .map(this::doExtrapolate)
                .reduce(0L, Long::sum);
    }

    private Long doExtrapolate(List<Long[]> out) {
        long lastNumber = 0;
        for (int i = out.size() - 2; i >= 0; i--) {
            Long[] longs = out.get(i);
            lastNumber = longs[0]-lastNumber;
        }
        return lastNumber;
    }

    public static void main(String[] args) {
        System.out.println(
                new Day9Part2().calculate("challenges\\day9\\input.txt")
        );
    }
}