package pl.pacinho.adventofcode2023.challange.day10.tools;

import lombok.RequiredArgsConstructor;
import pl.pacinho.adventofcode2023.challange.day10.model.Neighbor;
import pl.pacinho.adventofcode2023.challange.day10.model.NeighborType;
import pl.pacinho.adventofcode2023.challange.day10.model.Pipe;
import pl.pacinho.adventofcode2023.model.Pair;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
public class NeighborTools {

    private final Pipe[][] map;

    public boolean isConnectedWithNeighbors(Pipe pipe, int startPipeY, int startPipeX) {
        return pipe.getNeighbors()
                .stream()
                .allMatch(neighbor -> isConnectedWithNeighbor(neighbor, startPipeY, startPipeX));
    }

    public boolean isConnectedWithNeighbor(Neighbor neighbor, int startPipeY, int startPipeX) {
        Pipe neighborPipe = getPipe(getNeighborPipeLocation(neighbor.type(), startPipeY, startPipeX));
        if (neighborPipe == null)
            return false;

        return neighbor.pipes()
                .stream()
                .anyMatch(pipe -> neighborPipe.getSign().equals(pipe));
    }

    public Pipe getPipe(Pair<Integer, Integer> location) {
        if (location == null)
            return null;
        return map[location.key()][location.value()];
    }

    public Pair<Integer, Integer> getNeighborPipeLocation(NeighborType type, int y, int x) {
        int newY = y + type.getYOffset();
        int newX = x + type.getXOffset();

        if (newY < 0 || newX < 0)
            return null;

        if (newY >= map.length || newX >= map[0].length)
            return null;

        return new Pair<>(newY, newX);
    }

    public Pipe checkStartPipe(int startPipeY, int startPipeX) {
        return Arrays.stream(Pipe.values())
                .filter(pipe -> isConnectedWithNeighbors(pipe, startPipeY, startPipeX))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Cannot find start pipe!"));
    }


    public static String getLocationString(Pair<Integer, Integer> loc) {
        return loc.key() + "," + loc.value();
    }

    public List<Pair<Integer, Integer>> getAllNeighbors(int i, int j) {
        return Arrays.stream(NeighborType.values())
                .map(neighborType -> getNeighborPipeLocation(neighborType, i, j))
                .filter(Objects::nonNull)
                .toList();
    }
}
