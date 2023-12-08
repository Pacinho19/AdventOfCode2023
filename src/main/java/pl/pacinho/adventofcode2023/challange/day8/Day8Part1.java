package pl.pacinho.adventofcode2023.challange.day8;

import pl.pacinho.adventofcode2023.CalculateI;
import pl.pacinho.adventofcode2023.challange.day8.model.Side;
import pl.pacinho.adventofcode2023.challange.day8.tools.NavigationTools;
import pl.pacinho.adventofcode2023.challange.day8.tools.NetworkParser;
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
        navigate = NetworkParser.parseNavigate(lines);
        networkMap = NetworkParser.parseNetworkMap(lines);

        long counter = 0L;
        int navigationIdx = 0;
        String node = START_NODE;
        while (!node.equals(FINAL_NODE)) {
            Side side = navigate[navigationIdx];
            node = side.applyFunc(networkMap.get(node));
            counter++;
            navigationIdx = NavigationTools.nextNavigationIdx(navigate.length, navigationIdx);
        }

        return counter;
    }

    public static void main(String[] args) {
        System.out.println(
                new Day8Part1().calculate("challenges\\day8\\input.txt")
        );
    }
}
