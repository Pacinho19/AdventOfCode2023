package pl.pacinho.adventofcode2023.challange.day5;

import org.apache.commons.collections4.ListUtils;
import pl.pacinho.adventofcode2023.CalculateI;
import pl.pacinho.adventofcode2023.challange.day5.model.AlmanachDto;
import pl.pacinho.adventofcode2023.challange.day5.model.MapRangeDto;
import pl.pacinho.adventofcode2023.challange.day5.model.MapType;
import pl.pacinho.adventofcode2023.model.Pair;
import pl.pacinho.adventofcode2023.utils.DigitUtils;
import pl.pacinho.adventofcode2023.utils.FileUtils;

import java.io.File;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.LongStream;

public class Day5Part2 implements CalculateI {

    private AlmanachDto almanachDto;
    private MapType currentMapType = MapType.SEEDS;

    @Override
    public long calculate(String filePath) {
        almanachDto = new AlmanachDto();

        FileUtils.readTxt(new File(filePath))
                .stream()
                .filter(line -> !line.trim().isEmpty())
                .forEach(this::parseLine);

        return checkMinLocation();
    }

    private long checkMinLocation() {
        List<Map.Entry<MapType, List<MapRangeDto>>> sortedMap = almanachDto.getResourcesMap()
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey().getIndex() > 0)
                .sorted((e1, e2) -> Long.compare(e2.getKey().getIndex(), e1.getKey().getIndex()))
                .toList();

        for (long i = 0L; i < Long.MAX_VALUE; i++) {
            AtomicLong target = new AtomicLong(i);

            sortedMap.forEach(entry -> {
                long resourceValue = getResourceValue(target, entry.getValue());
                target.set(resourceValue);
            });


            if (isSeedInRange(target.get()))
                return i;
        }

        return 0L;
    }

    private boolean isSeedInRange(long l) {
        return almanachDto.getSeedRange()
                .stream()
                .anyMatch(range -> l >= range.key() && l <= range.value());
    }

    private long getResourceValue(AtomicLong target, List<MapRangeDto> mapRanges) {
        long targetL = target.get();

        MapRangeDto mapRangeDto = mapRanges.stream()
                .filter(mr -> targetL >= mr.destination() && targetL <= mr.destination() + mr.length() - 1)
                .findFirst()
                .orElse(null);

        return mapRangeDto == null ?
                targetL
                : mapRangeDto.source() + (targetL - mapRangeDto.destination());
    }

    private void parseLine(String line) {
        String mapTypeText = line.split(" ")[0];
        MapType mapType = MapType.fromText(mapTypeText);
        if (mapType != null)
            currentMapType = mapType;

        if (mapType == null)
            parseResources(line);
        else if (mapType == MapType.SEEDS)
            parseSeeds(line);

    }

    private void parseResources(String line) {
        String[] split = line.split(" ");

        long destination = Long.parseLong(split[0]);
        long source = Long.parseLong(split[1]);
        long length = Long.parseLong(split[2]);

        almanachDto.addResourcesMap(currentMapType, new MapRangeDto(destination, source, length));
    }

    private void parseSeeds(String line) {
        List<Long> seeds = Arrays.stream(line.split(" ", -1))
                .filter(DigitUtils::isDigit)
                .map(Long::parseLong)
                .toList();

        List<Pair<Long, Long>> seedsRange = ListUtils.partition(seeds, 2)
                .stream()
                .map(subList -> new Pair<>(subList.get(0), subList.get(0) + subList.get(1) - 1))
                .toList();

        almanachDto.setSeedRange(seedsRange);
        almanachDto.setSeeds(seeds);
    }


    public static void main(String[] args) {
        System.out.println(
                new Day5Part2().calculate("challenges\\day5\\input.txt")
        );
    }
}
