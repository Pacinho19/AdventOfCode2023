package pl.pacinho.adventofcode2023.challange.day11;

import pl.pacinho.adventofcode2023.CalculateI;
import pl.pacinho.adventofcode2023.challange.day11.model.PositionDto;
import pl.pacinho.adventofcode2023.challange.day11.tools.BFS;
import pl.pacinho.adventofcode2023.model.Pair;
import pl.pacinho.adventofcode2023.utils.FileUtils;
import pl.pacinho.adventofcode2023.utils.NeighborsUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day11Part1 implements CalculateI {

    private int colCount;

    private final int EXTENDED_VALUE;

    public Day11Part1(int extendedValue) {
        EXTENDED_VALUE = extendedValue;
    }

    @Override
    public long calculate(String filePath) {
        String[][] cosmic = FileUtils.readTxt(new File(filePath))
                .stream()
                .map(s -> s.split(""))
                .toArray(String[][]::new);

        colCount = cosmic[0].length;
        PositionDto[][] extendedCosmic = parseToPositionArr(cosmic);
        //ArrayUtils.print2DArray(cosmic);

        List<Integer> extendedRows = getExtendedRows(cosmic);
        List<Integer> extendedCols = getExtendedCols(cosmic);

        List<String> checked = new ArrayList<>();
        List<PositionDto> galactic = getGalactic(extendedCosmic);

        BFS bfs = new BFS(extendedCosmic.length * extendedCosmic[0].length, getVertexConnection(extendedCosmic));
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

                List<Integer> path = bfs.printShortestDistance(source, target);
                long count = path.stream().filter(extendedRows::contains).count() * (EXTENDED_VALUE-1);
                long count2 = path.stream().filter(extendedCols::contains).count() * (EXTENDED_VALUE-1);
                counter += path.size()-1 + count + count2;

                checked.add(getCheckedPositionString(startPosition, endPosition));
                checked.add(getCheckedPositionString(endPosition, startPosition));
            }
        }

        return counter;
    }

    private List<Integer> getExtendedCols(String[][] cosmic) {
        List<Integer> toExtend = new ArrayList<>();
        for (int x = 0; x < cosmic[0].length; x++) {
            boolean doExtend = true;
            for (int y = 0; y < cosmic.length; y++) {
                if (cosmic[y][x].equals("#")) {
                    doExtend = false;
                    break;
                }
            }

            if (doExtend)
                toExtend.add(x);
        }

        return toExtend.stream()
                .map(colIdx -> IntStream.range(0, cosmic.length).boxed().map(rowIdx -> rowIdx * colCount + colIdx).toList())
                .flatMap(List::stream)
                .toList();

    }

    private List<Integer> getExtendedRows(String[][] cosmic) {
        List<Integer> toExtend = new ArrayList<>();
        for (int y = 0; y < cosmic.length; y++) {
            if (!String.join("", cosmic[y]).contains("#"))
                toExtend.add(y);
        }

        return toExtend.stream()
                .map(rowIdx -> IntStream.range(0, colCount).boxed().map(colIdx -> rowIdx * colCount + colIdx).toList())
                .flatMap(List::stream)
                .toList();
    }

    private List<Pair<Integer, Integer>> getVertexConnection(PositionDto[][] extendedCosmic) {
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

    private PositionDto[][] parseToPositionArr(String[][] extendedArr) {
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

    public static void main(String[] args) {
        System.out.println(
                new Day11Part1(2).calculate("challenges\\day11\\input.txt")
        );
    }
}
