package pl.pacinho.adventofcode2023.challange.day5;

import pl.pacinho.adventofcode2023.CalculateI;
import pl.pacinho.adventofcode2023.challange.day5.model.AlmanachDto;
import pl.pacinho.adventofcode2023.challange.day5.model.MapRangeDto;
import pl.pacinho.adventofcode2023.challange.day5.model.MapType;
import pl.pacinho.adventofcode2023.utils.DigitUtils;
import pl.pacinho.adventofcode2023.utils.FileUtils;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class Day5Part1 implements CalculateI {

    private AlmanachDto almanachDto;
    private MapType currentMapType = MapType.SEEDS;

    @Override
    public long calculate(String filePath) {
        almanachDto = new AlmanachDto();

        FileUtils.readTxt(new File(filePath))
                .stream()
                .filter(line -> !line.trim().isEmpty())
                .forEach(this::parseLine);


        return almanachDto.getSeeds()
                .stream()
                .map(this::getLocation)
                .min(Long::compareTo)
                .orElseThrow(() -> new IllegalStateException("Could not find min location"));
    }

    private Long getLocation(Long seed) {
        AtomicLong target = new AtomicLong(seed);
        almanachDto.getResourcesMap()
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey().getIndex() > 0)
                .sorted(Comparator.comparing(entry -> entry.getKey().getIndex()))
                .forEach(entry -> target.set(getResourceValue(target, entry.getValue())));
        return target.get();
    }

    private long getResourceValue(AtomicLong target, List<MapRangeDto> mapRanges) {
        long targetL = target.get();

        MapRangeDto mapRangeDto = mapRanges.stream()
                .filter(mr -> targetL>=mr.source() && targetL<=mr.source() + mr.length()-1)
                .findFirst()
                .orElse(null);

        return mapRangeDto==null ?
                targetL
                : mapRangeDto.destination() + (targetL - mapRangeDto.source());
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
        List<Long> seeds = Arrays.stream(line.split(" "))
                .filter(DigitUtils::isDigit)
                .map(Long::parseLong)
                .toList();

        almanachDto.setSeeds(seeds);
    }

    public static void main(String[] args) {
        System.out.println(
                new Day5Part1().calculate("challenges\\day5\\input.txt")
        );
    }
}
