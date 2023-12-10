package pl.pacinho.adventofcode2023.challange.day10;

import pl.pacinho.adventofcode2023.CalculateI;
import pl.pacinho.adventofcode2023.challange.day10.model.Neighbor;
import pl.pacinho.adventofcode2023.challange.day10.model.NeighborType;
import pl.pacinho.adventofcode2023.challange.day10.model.Pipe;
import pl.pacinho.adventofcode2023.model.Pair;
import pl.pacinho.adventofcode2023.utils.FileUtils;

import java.io.File;
import java.util.*;

public class Day10Part1 implements CalculateI {

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
        if (getPipe(location) == Pipe.START && distance > 0L)
            return;

        Pair<Integer, Integer> neighborPipeLocation = getNeighborPipeLocation(neighbor.type(), location.key(), location.value());
        String locationString = getLocationString(neighborPipeLocation);
        Pipe pipe = getPipe(neighborPipeLocation);

        System.out.println(locationString);

        neighbor.pipes()
                .stream()
                .filter(neighborPipe -> pipe.getSign().equals(neighborPipe) && !visited.contains(locationString))
                .forEach(neighborPipe -> {

                    distance++;

                    Long aLong = moveMap.computeIfAbsent(locationString, key -> distance);
                    if (distance < aLong)
                        moveMap.put(locationString, distance);

                    visited.add(locationString);

                    Pipe.findBySign(neighborPipe).getNeighbors()
                            .forEach(neigh -> moveToNextPipe(neighborPipeLocation, neigh));

                });

    }

    private String getLocationString(Pair<Integer, Integer> loc) {
        return loc.key() + "," + loc.value();
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
                checkStartPipe(startPipeY, startPipeX),
                new Pair<>(startPipeY, startPipeX)
        );
    }

    private Pipe checkStartPipe(int startPipeY, int startPipeX) {
        return Arrays.stream(Pipe.values())
                .filter(pipe -> isConnectedWithNeighbors(pipe, startPipeY, startPipeX))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Cannot find start pipe!"));

    }

    private boolean isConnectedWithNeighbors(Pipe pipe, int startPipeY, int startPipeX) {
        return pipe.getNeighbors()
                .stream()
                .allMatch(neighbor -> isConnectedWithNeighbor(neighbor, startPipeY, startPipeX));
    }

    private boolean isConnectedWithNeighbor(Neighbor neighbor, int startPipeY, int startPipeX) {
        Pipe neighborPipe = getPipe(getNeighborPipeLocation(neighbor.type(), startPipeY, startPipeX));
        if (neighborPipe == null)
            return false;

        return neighbor.pipes()
                .stream()
                .anyMatch(pipe -> neighborPipe.getSign().equals(pipe));
    }

    private Pipe getPipe(Pair<Integer, Integer> location) {
        if (location == null)
            return null;
        return map[location.key()][location.value()];
    }

    private Pair<Integer, Integer> getNeighborPipeLocation(NeighborType type, int y, int x) {
        int newY = y + type.getYOffset();
        int newX = x + type.getXOffset();

        if (newY < 0 || newX < 0)
            return null;

        if (newY >= map.length || newX >= map[0].length)
            return null;

        return new Pair<>(newY, newX);
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