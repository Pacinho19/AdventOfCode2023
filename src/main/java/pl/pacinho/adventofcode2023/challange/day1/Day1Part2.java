package pl.pacinho.adventofcode2023.challange.day1;

import pl.pacinho.adventofcode2023.CalculateI;
import pl.pacinho.adventofcode2023.utils.DigitUtils;
import pl.pacinho.adventofcode2023.utils.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Day1Part2 implements CalculateI {

    private Map<String, Long> digitMap = Map.ofEntries(
            Map.entry("one", 1L),
            Map.entry("two", 2L),
            Map.entry("three", 3L),
            Map.entry("four", 4L),
            Map.entry("five", 5L),
            Map.entry("six", 6L),
            Map.entry("seven", 7L),
            Map.entry("eight", 8L),
            Map.entry("nine", 9L)
    );

    @Override
    public long calculate(String filePath) {
        return FileUtils.readTxt(new File(filePath))
                .stream()
                .map(this::extractDigitWords)
                .peek(System.out::println)
                .reduce(0L, Long::sum);
    }

    private long extractDigitWords(String s) {
        String tempS = "";
        List<Long> finalNumber = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (DigitUtils.isDigit(String.valueOf(c))) {
                finalNumber.add(Long.valueOf(String.valueOf(c)));
                tempS = "";
                continue;
            }

            tempS += c;
            Long digitFromWord = getDigitFromWordMap(tempS);
            if (digitFromWord == -1)
                continue;

            finalNumber.add(digitFromWord);
            tempS = tempS.substring(tempS.length()-1); //last letter may be first letter of next digit
        }
        return Long.parseLong(String.valueOf(finalNumber.get(0)) + finalNumber.get(finalNumber.size() - 1));
    }

    private Long getDigitFromWordMap(String tempS) {
        return digitMap.entrySet()
                .stream()
                .filter(entry -> tempS.contains(entry.getKey()))
                .findFirst()
                .map(Map.Entry::getValue)
                .orElse(-1L);
    }

    public static void main(String[] args) {
        System.out.println(
                new Day1Part2().calculate("challenges\\day1\\input.txt")
        );
    }
}