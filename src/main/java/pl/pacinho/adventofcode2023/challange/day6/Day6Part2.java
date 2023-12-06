package pl.pacinho.adventofcode2023.challange.day6;

import pl.pacinho.adventofcode2023.CalculateI;
import pl.pacinho.adventofcode2023.challange.day6.model.RaceDto;
import pl.pacinho.adventofcode2023.utils.FileUtils;

import java.io.File;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class Day6Part2 implements CalculateI {

    private RaceDto raceDto;

    @Override
    public long calculate(String filePath) {
        raceDto = new RaceDto();

        FileUtils.readTxt(new File(filePath))
                .forEach(this::parseLine);

        return checkWiningRaceWaysCount(raceDto);
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

    private void parseLine(String line) {
        String[] split = line.split(":");

        if (split[0].equals("Time")) {
            raceDto.setTime(parseNumber(split[1]));
        } else if (split[0].equals("Distance")) {
            raceDto.setDistance(parseNumber(split[1]));
        }
    }

    private Long parseNumber(String line) {
        String[] arr = line.trim().split(" ");
        return Long.valueOf(
                Arrays.stream(arr)
                        .filter(s -> !s.isEmpty())
                        .collect(Collectors.joining(""))
        );
    }

    public static void main(String[] args) {
        System.out.println(
                new Day6Part2().calculate("challenges\\day6\\input.txt")
        );
    }
}
