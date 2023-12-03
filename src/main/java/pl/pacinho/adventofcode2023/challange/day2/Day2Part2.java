package pl.pacinho.adventofcode2023.challange.day2;

import pl.pacinho.adventofcode2023.CalculateI;
import pl.pacinho.adventofcode2023.challange.day2.model.GameDto;
import pl.pacinho.adventofcode2023.utils.FileUtils;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day2Part2 implements CalculateI {
    @Override
    public long calculate(String filePath) {
        return FileUtils.readTxt(new File(filePath))
                .stream()
                .map(this::parseGame)
                .map(this::calculatePower)
                .reduce(0L, Long::sum);
    }

    private long calculatePower(GameDto gameDto) {
        Map<String, Long> maxColors = new HashMap<>();
        gameDto.sets()
                .forEach(set -> set.forEach((key, value) -> {
                            Long aLong = maxColors.computeIfAbsent(key, key2 -> Long.valueOf(value));
                            if (value > aLong)
                                maxColors.put(key, Long.valueOf(value));
                        })
                );

        return maxColors.values()
                .stream()
                .reduce(1L, (l1, l2) -> l1 * l2);
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
                new Day2Part2().calculate("challenges\\day2\\input.txt")
        );
    }
}