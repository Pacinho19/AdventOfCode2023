package pl.pacinho.adventofcode2023.challange.day6;

import pl.pacinho.adventofcode2023.CalculateI;
import pl.pacinho.adventofcode2023.challange.day6.model.InputDto;
import pl.pacinho.adventofcode2023.challange.day6.model.RaceDto;
import pl.pacinho.adventofcode2023.utils.FileUtils;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class Day6Part1 implements CalculateI {

    private InputDto inputDto;

    @Override
    public long calculate(String filePath) {
        inputDto = new InputDto();

        FileUtils.readTxt(new File(filePath))
                .forEach(this::parseLine);

        List<RaceDto> races = parseRaces();

        return races.stream()
                .map(this::checkWiningRaceWaysCount)
                .reduce(1L, (l1, l2) -> l1 * l2);
    }

    private long checkWiningRaceWaysCount(RaceDto raceDto) {
        return LongStream.rangeClosed(0, raceDto.getTime())
                .boxed()
                .map(holdButtonTime -> checkRaceWay(holdButtonTime, raceDto.getTime()))
                .filter(distance -> distance > raceDto.getDistance())
                .count();
    }

    private long checkRaceWay(Long holdButtonTime, long raceTime) {
        return (raceTime - holdButtonTime) * holdButtonTime;
    }

    private List<RaceDto> parseRaces() {
        int size = inputDto.getTimes().size();
        return IntStream.range(0, size)
                .boxed()
                .map(i -> new RaceDto(inputDto.getTimes().get(i), inputDto.getDistances().get(i)))
                .toList();
    }

    private void parseLine(String line) {
        String[] split = line.split(":");

        if (split[0].equals("Time")) {
            inputDto.setTimes(extractNumbers(split[1]));
        } else if (split[0].equals("Distance")) {
            inputDto.setDistances(extractNumbers(split[1]));
        }
    }

    private LinkedList<Long> extractNumbers(String line) {
        String[] arr = line.trim().split(" ");
        return Arrays.stream(arr)
                .filter(s -> !s.isEmpty())
                .map(Long::parseLong)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    public static void main(String[] args) {
        System.out.println(
                new Day6Part1().calculate("challenges\\day6\\input.txt")
        );
    }
}
