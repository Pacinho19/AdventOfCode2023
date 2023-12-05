package pl.pacinho.adventofcode2023.challange.day5.model;

import pl.pacinho.adventofcode2023.model.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlmanachDto {
    private List<Long> seeds;
    private List<Pair<Long, Long>> seedRange;
    private Map<MapType, List<MapRangeDto>> resourcesMap;

    public AlmanachDto() {
        resourcesMap = new HashMap<>();
    }

    public List<Long> getSeeds() {
        return seeds;
    }

    public void setSeeds(List<Long> seeds) {
        this.seeds = seeds;
    }

    public List<Pair<Long, Long>> getSeedRange() {
        return seedRange;
    }

    public Map<MapType, List<MapRangeDto>> getResourcesMap() {
        return resourcesMap;
    }

    public void setSeedRange(List<Pair<Long, Long>> seedRange) {
        this.seedRange = seedRange;
    }

    public void addResourcesMap(MapType mapType, MapRangeDto mapRangeDto) {
        List<MapRangeDto> longLongMap = this.resourcesMap.computeIfAbsent(mapType, key -> new ArrayList<>());
        longLongMap.add(mapRangeDto);
    }
}
