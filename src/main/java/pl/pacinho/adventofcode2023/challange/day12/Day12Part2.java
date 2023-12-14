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

public class Day12Part2 implements CalculateI {

    //4037908270468 to low
    //488630347251 to low
    //10862589935073 to high
    //948249483211 bad
    //10862580240990 bad
    //10840056708890 bad
    //10840036202331 bad
    //10833487103067 bad
    //533462958425 bad
    //1369451326499 bad
    //10833466849497 bad

    @Override
    public long calculate(String filePath) {
        return FileUtils.readTxt(new File(filePath))
                .stream()
                .map(this::parseGearRow)
                .map(this::calculateGearStateOption)
                .reduce(0L, Long::sum);
    }

    private long calculateGearStateOption(GearRowDto gearRowDto) {
        LinkedList<Integer> statuses = gearRowDto.statuses();

        long counterMain = calculateCombination(gearRowDto.gearLine(), statuses);

        String gearSignLine2 = extendGearArray(gearRowDto.gearLine());

        Integer integer = statuses.get(statuses.size()-1);
        if (gearRowDto.gearLine().endsWith("#"))
            statuses.add(0, integer);


        long counterExtended = calculateCombination(gearSignLine2, statuses);
//        System.out.println(out);
        return (long) Math.pow(counterExtended, 4) * counterMain;
    }

    private long calculateCombination(String gearSignLine, LinkedList<Integer> statuses) {
        long counter = 0L;

        String[] gearSignArr = gearSignLine.split("");
        int unknownStateCount = getUnknownStateCount(gearSignLine);
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

            if (isCorrectGearLine(newLine, statuses)) {
                counter++;
            }
        }

        return counter;
    }

    private String extendGearArray(String s) {
        if (s.endsWith("#"))
            return getLastBadGears(s) + "?" + s;
        else if (s.endsWith("?"))
            return "?" + s + s.charAt(0);
        return "?" + s;
    }

    private String getLastBadGears(String s) {
        String[] split = s.split("");
        String out = "";
        for (int i = split.length - 1; i >= 0; i--) {
            if (split[i].equals("#"))
                out = out + "#";
            else
                break;
        }
        return out;
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
                new Day12Part2().calculate("challenges\\day12\\input.txt")
        );
    }
}