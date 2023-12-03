package pl.pacinho.adventofcode2023.challange.day2;

import pl.pacinho.adventofcode2023.CalculateI;
import pl.pacinho.adventofcode2023.challange.day2.model.GameDto;
import pl.pacinho.adventofcode2023.utils.DigitUtils;
import pl.pacinho.adventofcode2023.utils.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day2Part1 implements CalculateI {

    private final Map<String, Integer> CUBES_MAP = Map.ofEntries(
            Map.entry("red", 12),
            Map.entry("green", 13),
            Map.entry("blue", 14)
    );

    @Override
    public long calculate(String filePath) {
        return FileUtils.readTxt(new File(filePath))
                .stream()
                .map(this::parseGame)
                .filter(this::isCorrectGame)
                .map(GameDto::idx)
                .reduce(0L, Long::sum);
    }

    private boolean isCorrectGame(GameDto gameDto) {
        return gameDto.sets()
                .stream()
                .noneMatch(this::isIncorrectSet);
    }

    private boolean isIncorrectSet(Map<String, Integer> set) {
        return set.entrySet()
                .stream()
                .anyMatch(entry -> CUBES_MAP.get(entry.getKey()) < entry.getValue());
    }

    private GameDto parseGame(String s) {
        long idx = Long.parseLong(s.split(":")[0].split(" ")[1]);
        List<Map<String, Integer>> sets = Arrays.stream(s.split(":")[1].split(";"))
                .map(s2 -> parseSet(s2.split(",")))
                .toList();
        return new GameDto(idx, sets);
    }

    private Map<String, Integer> parseSet(String[] array) {
        return Arrays.stream(array)
                .collect(Collectors.toMap(
                        key -> key.trim().split(" ")[1],
                        value -> Integer.parseInt(value.trim().split(" ")[0])));
    }

    public static void main(String[] args) {
        System.out.println(
                new Day2Part1().calculate("challenges\\day2\\input.txt")
        );
    }
}