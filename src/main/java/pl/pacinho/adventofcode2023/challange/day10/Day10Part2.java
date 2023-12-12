package pl.pacinho.adventofcode2023.challange.day10;

import pl.pacinho.adventofcode2023.CalculateI;
import pl.pacinho.adventofcode2023.challange.day10.model.Neighbor;
import pl.pacinho.adventofcode2023.challange.day10.model.Pipe;
import pl.pacinho.adventofcode2023.challange.day10.tools.NeighborTools;
import pl.pacinho.adventofcode2023.model.Pair;
import pl.pacinho.adventofcode2023.utils.FileUtils;

import java.awt.*;
import java.io.File;
import java.util.*;
import java.util.List;

public class Day10Part2 implements CalculateI {
    private NeighborTools neighborTools;
    private Pipe[][] map;
    private Map<String, Long> moveMap;
    private Long distance = 0L;
    private List<String> visited;

    @Override
    public long calculate(String filePath) {
        moveMap = new LinkedHashMap<>();
        map = FileUtils.readTxt(new File(filePath))
                .stream()
                .map(this::parseLine)
                .toArray(Pipe[][]::new);

        neighborTools = new NeighborTools(map);

        goMove();

        return countTiles();
    }

    private long countTiles() {
        Polygon polygon = initPolygonPoints();
        long counter = 0L;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {

                if (moveMap.containsKey(NeighborTools.getLocationString(new Pair<>(i, j))) || neighborTools.getAllNeighbors(i, j).size() < 4)
                    continue;

                if (isPointInPolygon(new Point(j, i), polygon))
                    counter++;
            }
        }

        return counter;
    }

    private boolean isPointInPolygon(Point p, Polygon polygon) {
        return polygon.contains(p);
    }

    private Polygon initPolygonPoints() {
        List<String> locations = new ArrayList<>(moveMap.keySet());

        Polygon polygon = new Polygon();

        for (int i = 0; i < locations.size(); i++) {
            String[] split = locations.get(i).split(",");
            polygon.addPoint(Integer.parseInt(split[1]), Integer.parseInt(split[0]));
        }

        return polygon;
    }

    private void goMove() {
        Pair<Pipe, Pair<Integer, Integer>> startPipe = checkStartPipeLocation();
        moveMap.put(NeighborTools.getLocationString(startPipe.value()), 0L);

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

            final Pair<Integer, Integer> neighborPipeLocation = neighborTools.getNeighborPipeLocation(neighbor.type(), location.key(), location.value());
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
                new Day10Part2().calculate("challenges\\day10\\input.txt")
        );
    }

}