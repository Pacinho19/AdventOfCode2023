package pl.pacinho.adventofcode2023.challange.day12;

import pl.pacinho.adventofcode2023.CalculateI;
import pl.pacinho.adventofcode2023.challange.day12.model.GearRowDto;
import pl.pacinho.adventofcode2023.utils.DigitUtils;
import pl.pacinho.adventofcode2023.utils.FileUtils;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day12Part1 implements CalculateI {
    @Override
    public long calculate(String filePath) {
        return FileUtils.readTxt(new File(filePath))
                .stream()
                .map(this::parseGearRow)
                .map(this::calculateGearStateOption)
                .reduce(0L, Long::sum);
    }

    private long calculateGearStateOption(GearRowDto gearRowDto) {
        long counter = 0L;
        LinkedList<Integer> statuses = gearRowDto.statuses();
        int unknownStateCount = getUnknownStateCount(gearRowDto.gearLine());
        String[] gearSignArr = gearRowDto.gearLine().split("");

        int operationCount = (int) Math.pow(2, unknownStateCount);
        for (int i = 0; i < operationCount; i++) {
            Integer[] binary = DigitUtils.decimalToBinary(i, unknownStateCount);

            String[] newLine = Arrays.copyOf(gearSignArr, gearSignArr.length);
            int binaryIdx = 0;
            for (int j = 0; j < newLine.length; j++) {
                String s = newLine[j];

                if (!s.equals("?"))
                    continue;

                newLine[j] = binary[binaryIdx] == 1 ? "." : "#";
                binaryIdx++;
            }

            if (isCorrectGearLine(newLine, statuses))
                counter++;
        }

        return counter;
    }

    private boolean isCorrectGearLine(String[] gearLineArr, LinkedList<Integer> statuses) {
        String gearLine = String.join("", gearLineArr);
        String[] split = Arrays.stream(gearLine.split("\\.")).filter(s -> !s.isEmpty()).toArray(String[]::new);

        if (split.length != statuses.size())
            return false;

        for (int i = 0; i < split.length; i++) {
            if (split[i].length() != statuses.get(i))
                return false;
        }

        return true;
    }

    private int getUnknownStateCount(String gearLine) {
        return (int) Arrays.stream(gearLine.split(""))
                .filter(s -> s.equals("?"))
                .count();
    }

    private GearRowDto parseGearRow(String s) {
        String[] split = s.split(" ");
        return new GearRowDto(
                split[0],
                parseIntList(split[1])
        );
    }

    private LinkedList<Integer> parseIntList(String s) {
        return Arrays.stream(s.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    public static void main(String[] args) {
        System.out.println(
                new Day12Part1().calculate("challenges\\day12\\input.txt")
        );
    }
}