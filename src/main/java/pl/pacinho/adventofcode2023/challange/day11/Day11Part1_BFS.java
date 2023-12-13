package pl.pacinho.adventofcode2023.challange.day11;

import pl.pacinho.adventofcode2023.CalculateI;
import pl.pacinho.adventofcode2023.challange.day11.model.PositionDto;
import pl.pacinho.adventofcode2023.challange.day11.tools.AreaDijkstry;
import pl.pacinho.adventofcode2023.challange.day11.tools.BFS;
import pl.pacinho.adventofcode2023.model.Pair;
import pl.pacinho.adventofcode2023.utils.FileUtils;
import pl.pacinho.adventofcode2023.utils.NeighborsUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Day11Part1_BFS implements CalculateI {
    @Override
    public long calculate(String filePath) {
        String[][] cosmic = FileUtils.readTxt(new File(filePath))
                .stream()
                .map(Day11Part1_BFS::extendRow)
                .flatMap(List::stream)
                .toArray(String[][]::new);

        PositionDto[][] extendedCosmic = extendColumn(cosmic);
        //ArrayUtils.print2DArray(cosmic);

        List<String> checked = new ArrayList<>();
        List<PositionDto> galactic = getGalactic(extendedCosmic);

        BFS bfs = new BFS(extendedCosmic.length * extendedCosmic[0].length, getVertexConnection(extendedCosmic));

        int colCount =  extendedCosmic[0].length;

        long counter = 0;
        for (int i = 0; i < galactic.size() - 1; i++) {
            PositionDto startPosition = galactic.get(i);

            for (int j = 0; j < galactic.size(); j++) {
                if (i == j) continue;

                PositionDto endPosition = galactic.get(j);

                if (checked.contains(getCheckedPositionString(startPosition, endPosition)))
                    continue;

                int source = (startPosition.row() * colCount + startPosition.col());
                int target = (endPosition.row() * colCount + endPosition.col());

                int count = bfs.printShortestDistance(source, target);
                counter += count;

                checked.add(getCheckedPositionString(startPosition, endPosition));
                checked.add(getCheckedPositionString(endPosition, startPosition));
            }
        }

        return counter;
    }

    private List<Pair<Integer, Integer>> getVertexConnection(PositionDto[][] extendedCosmic) {
        int colCount = extendedCosmic[0].length;

        NeighborsUtils neighborsUtils = new NeighborsUtils(colCount, extendedCosmic.length);

        List<Pair<Integer, Integer>> out = new ArrayList<>();

        for (int y = 0; y < extendedCosmic.length; y++) {
            for (int x = 0; x < extendedCosmic[y].length; x++) {
                int idx = (y * colCount + x);
                neighborsUtils.getNeighborsSimple(y, x)
                        .forEach(pos -> out.add(new Pair<>(idx, (pos.y() * colCount + pos.x()))));
            }
        }
        return out;
    }

    private String getCheckedPositionString(PositionDto startPosition, PositionDto endPosition) {
        return startPosition.getPosAsString() + "|" + endPosition.getPosAsString();
    }


    private List<PositionDto> getGalactic(PositionDto[][] extendedCosmic) {
        return Stream.of(extendedCosmic)
                .flatMap(Arrays::stream)
                .filter(pos -> pos.sign().equals("#"))
                .toList();
    }

    private PositionDto[][] extendColumn(String[][] cosmic) {
        List<List<String>> out = new ArrayList<>();

        List<Integer> colToExtend = new ArrayList<>();
        for (int x = 0; x < cosmic[0].length; x++) {
            boolean doExtend = true;
            for (int y = 0; y < cosmic.length; y++) {
                if (cosmic[y][x].equals("#")) {
                    doExtend = false;
                    break;
                }
            }

            if (doExtend)
                colToExtend.add(x);
        }

        for (int y = 0; y < cosmic.length; y++) {

            List<String> row = out.size() > y ? out.get(y) : new ArrayList<>();
            for (int x = 0; x < cosmic[y].length; x++) {
                row.add(cosmic[y][x]);
                if (colToExtend.contains(x))
                    row.add(".");
            }

            out.add(row);
        }

        String[][] extendedArr = out.stream()
                .map(list -> list.toArray(String[]::new))
                .toArray(String[][]::new);

        List<List<PositionDto>> out2 = new ArrayList<>();
        for (int y = 0; y < extendedArr.length; y++) {
            List<PositionDto> row = new ArrayList<>();
            for (int x = 0; x < extendedArr[y].length; x++) {
                row.add(new PositionDto(y, x, extendedArr[y][x]));
            }
            out2.add(row);
        }

        return out2.stream()
                .map(list -> list.toArray(PositionDto[]::new))
                .toArray(PositionDto[][]::new);
    }

    private static List<String[]> extendRow(String s) {
        List<String[]> out = new ArrayList<>();
        String[] split = s.split("");

        out.add(split);
        if (!s.contains("#"))
            out.add(split);
        return out;
    }

    public static void main(String[] args) {
        System.out.println(
                new Day11Part1_BFS().calculate("challenges\\day11\\input.txt")
        );
    }
}
