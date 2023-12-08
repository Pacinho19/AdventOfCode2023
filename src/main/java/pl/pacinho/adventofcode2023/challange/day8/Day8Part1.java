package pl.pacinho.adventofcode2023.challange.day8;

import pl.pacinho.adventofcode2023.CalculateI;
import pl.pacinho.adventofcode2023.challange.day8.model.Side;
import pl.pacinho.adventofcode2023.model.Pair;
import pl.pacinho.adventofcode2023.utils.FileUtils;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day8Part1 implements CalculateI {

    private final String FINAL_NODE = "ZZZ";
    private final String START_NODE = "AAA";
    private Side[] navigate;
    private Map<String, Pair<String, String>> networkMap;

    @Override
    public long calculate(String filePath) {
        List<String> lines = FileUtils.readTxt(new File(filePath));
        navigate = parseNavigate(lines);
        networkMap = parseNetworkMap(lines);

        long counter = 0L;
        int navigationIdx = 0;
        String node = START_NODE;
        while (!node.equals(FINAL_NODE)) {
            Side side = navigate[navigationIdx];
            node = side.applyFunc(networkMap.get(node));
            counter++;
            navigationIdx = nextNavigationIdx(navigationIdx);
        }

        return counter;
    }

    private int nextNavigationIdx(int navigationIdx) {
        return navigationIdx == navigate.length - 1
                ? 0
                : navigationIdx + 1;
    }

    private static Side[] parseNavigate(List<String> lines) {
        return Arrays.stream(lines.get(0).split(""))
                .map(Side::getSide)
                .toArray(Side[]::new);
    }

    private Map<String, Pair<String, String>> parseNetworkMap(List<String> lines) {
        return lines.stream()
                .skip(1)
                .filter(line -> !line.isEmpty())
                .collect(Collectors.toMap(line -> line.split("=")[0].trim(), this::parseNavigate));
    }

    private Pair<String, String> parseNavigate(String line) {
        String[] split = line.split("=")[1].trim()
                .replace("(", "")
                .replace(")", "")
                .split(",");
        return new Pair<>(split[0].trim(), split[1].trim());
    }

    public static void main(String[] args) {
        System.out.println(
                new Day8Part1().calculate("challenges\\day8\\input.txt")
        );
    }
}
