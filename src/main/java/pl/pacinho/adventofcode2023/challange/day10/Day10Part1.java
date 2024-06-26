package pl.pacinho.adventofcode2023.challange.day10;

import pl.pacinho.adventofcode2023.CalculateI;
import pl.pacinho.adventofcode2023.challange.day10.model.Neighbor;
import pl.pacinho.adventofcode2023.challange.day10.model.Pipe;
import pl.pacinho.adventofcode2023.challange.day10.tools.NeighborTools;
import pl.pacinho.adventofcode2023.model.Pair;
import pl.pacinho.adventofcode2023.utils.FileUtils;

import java.io.File;
import java.util.*;

public class Day10Part1 implements CalculateI {

    private NeighborTools neighborTools;
    private Pipe[][] map;
    private Map<String, Long> moveMap;
    private Long distance = 0L;
    private List<String> visited;

    @Override
    public long calculate(String filePath) {
        moveMap = new HashMap<>();
        map = FileUtils.readTxt(new File(filePath))
                .stream()
                .map(this::parseLine)
                .toArray(Pipe[][]::new);

        neighborTools = new NeighborTools(map);

        goMove();

        return moveMap.values()
                .stream()
                .max(Long::compareTo)
                .orElse(0L);
    }

    private void goMove() {
        Pair<Pipe, Pair<Integer, Integer>> startPipe = checkStartPipeLocation();
        startPipe.key()
                .getNeighbors()
                .forEach(neighbor -> {
                    distance = 0L;
                    visited = new ArrayList<>();
                    moveToNextPipe(startPipe.value(), neighbor);
                });
    }

    private void moveToNextPipe(Pair<Integer, Integer> location, Neighbor neighbor) {
        while (true) {
            if (neighborTools.getPipe(location) == Pipe.START && distance > 0L)
                return;

            final Pair<Integer, Integer> neighborPipeLocation =neighborTools.getNeighborPipeLocation(neighbor.type(), location.key(), location.value());
            String locationString = NeighborTools.getLocationString(neighborPipeLocation);
            Pipe pipe = neighborTools.getPipe(neighborPipeLocation);


            String neighborPipe2 = neighbor.pipes()
                    .stream()
                    .filter(neighborPipe -> pipe.getSign().equals(neighborPipe) && !visited.contains(locationString))
                    .findFirst()
                    .orElse(null);

            if (neighborPipe2 == null)
                continue;

            distance++;

            Long aLong = moveMap.computeIfAbsent(locationString, key -> distance);
            if (distance < aLong)
                moveMap.put(locationString, distance);

            visited.add(locationString);

            final Neighbor newNeighbor = Pipe.findBySign(neighborPipe2).getNeighbors()
                    .stream()
                    .filter(neigh -> isValidNeighbor(neigh, neighborPipeLocation.key(), neighborPipeLocation.value()))
                    .findFirst()
                    .orElse(null);

            if (newNeighbor == null)
                return;

            location = neighborPipeLocation;
            neighbor = newNeighbor;
        }
    }

    private boolean isValidNeighbor(Neighbor neigh, Integer y, Integer x) {
        Pair<Integer, Integer> neighborPipeLocation = neighborTools.getNeighborPipeLocation(neigh.type(), y, x);
        return neighborTools.isConnectedWithNeighbor(neigh, y, x)
               && !visited.contains(NeighborTools.getLocationString(neighborPipeLocation));
    }


    private Pair<Pipe, Pair<Integer, Integer>> checkStartPipeLocation() {
        int startPipeY = 0;
        int startPipeX = 0;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == Pipe.START) {
                    startPipeY = i;
                    startPipeX = j;
                    break;
                }
            }
            if (startPipeY > 0)
                break;
        }

        return new Pair<>(
                neighborTools.checkStartPipe(startPipeY, startPipeX),
                new Pair<>(startPipeY, startPipeX)
        );
    }

    private Pipe[] parseLine(String s) {
        return Arrays.stream(s.split(""))
                .map(Pipe::findBySign)
                .toArray(Pipe[]::new);
    }

    public static void main(String[] args) {
        System.out.println(
                new Day10Part1().calculate("challenges\\day10\\input.txt")
        );
    }

}