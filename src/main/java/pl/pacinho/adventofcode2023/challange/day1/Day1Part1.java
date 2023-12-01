package pl.pacinho.adventofcode2023.challange.day1;

import pl.pacinho.adventofcode2023.CalculateI;
import pl.pacinho.adventofcode2023.utils.DigitUtils;
import pl.pacinho.adventofcode2023.utils.FileUtils;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class Day1Part1 implements CalculateI {
    @Override
    public long calculate(String filePath) {
        return FileUtils.readTxt(new File(filePath))
                .stream()
                .map(this::extractDigit)
                .reduce(0L, Long::sum);
    }

    public long extractDigit(String line) {
        List<String> list = Arrays.stream(line.split(""))
                .filter(DigitUtils::isDigit)
                .toList();
        return Long.parseLong(list.get(0) + list.get(list.size() - 1));
    }

    public static void main(String[] args) {
        System.out.println(
                new Day1Part1().calculate("challenges\\day1\\input.txt")
        );
    }
}