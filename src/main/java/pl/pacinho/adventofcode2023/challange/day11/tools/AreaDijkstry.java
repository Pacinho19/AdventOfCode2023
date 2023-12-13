package pl.pacinho.adventofcode2023.challange.day11.tools;

import org.apache.commons.lang3.tuple.ImmutablePair;
import pl.pacinho.adventofcode2023.challange.day11.model.PositionDto;

import java.util.*;
import java.util.stream.IntStream;

public class AreaDijkstry {

    private LinkedHashMap<String, Integer> dV;
    private LinkedHashMap<String, String> pV;

    private static final List<ImmutablePair> NEIGHBORS = List.of(
            new ImmutablePair<>(-1, 0),
            new ImmutablePair<>(0, -1),
            new ImmutablePair<>(0, 1),
            new ImmutablePair<>(1, 0)
    );

    private PositionDto[][] area;
    private List<String> completed;
    private int ROW_COUNT;
    private int COL_COUNT;

    public AreaDijkstry(PositionDto[][] area, PositionDto startLocation) {
        this.area = area;
        initParams();
        fill_dV();
        fill_pV();

        checkPaths(startLocation.row() + "," + startLocation.col());
    }

    public long getMinPath(String endLocation) {
        return dV.get(endLocation);
    }

    private void initParams() {
        this.ROW_COUNT = area.length;
        this.COL_COUNT = area[0].length;
    }

    private void checkPaths(String startLocation) {
        dV.put(startLocation, 0);
        completed = new ArrayList<>();
        while (completed.size() < ROW_COUNT * COL_COUNT) {
            String key = Collections.min(
                    dV.entrySet()
                            .stream()
                            .filter(k -> !completed.contains(k.getKey()))
                            .toList()
                    , Map.Entry.comparingByValue()).getKey();
            checkPoint(key);
        }

    }

    private void checkPoint(String point) {
        List<String> nextWays = getNextWays(point);
        nextWays.forEach(newPoint -> {
            Integer cost = 1;
            Integer oldPointCost = dV.get(newPoint);

            if (dV.get(point) + cost < oldPointCost) {
                dV.put(newPoint, dV.get(point) + cost);
                pV.put(newPoint, point);
            }
        });
        completed.add(point);
    }

    private List<String> getNextWays(String point) {
        return getNeighbors(point)
                .stream()
                .map(p -> p.row() + "," + p.col())
                .toList();
    }

    private List<PositionDto> getNeighbors(String pos) {
        return NEIGHBORS.stream()
                .map(i -> checkNeighbor(pos, i))
                .filter(Objects::nonNull)
                .toList();
    }

    private PositionDto checkNeighbor(String currentPos, ImmutablePair<Integer, Integer> i) {
        String[] split = currentPos.split(",");
        int row = Integer.parseInt(split[0]) + i.getLeft();
        int col = Integer.parseInt(split[1]) + i.getRight();

        if (row < 0 || row >= area.length
            || col < 0 || col >= area[0].length) return null;

        return  area[row][col];
    }

    private void fill_pV() {
        pV = new LinkedHashMap<>();
        IntStream.range(0, ROW_COUNT)
                .forEach(row -> IntStream.range(0, COL_COUNT)
                        .forEach(col -> pV.put(row + "," + col, "-")));
    }

    private void fill_dV() {
        dV = new LinkedHashMap<>();
        IntStream.range(0, ROW_COUNT)
                .forEach(row -> IntStream.range(0, COL_COUNT)
                        .forEach(col -> dV.put(row + "," + col, Integer.MAX_VALUE)));
    }

}
