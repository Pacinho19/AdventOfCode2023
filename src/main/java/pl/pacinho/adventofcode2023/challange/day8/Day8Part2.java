package pl.pacinho.adventofcode2023.challange.day8;

import pl.pacinho.adventofcode2023.CalculateI;
import pl.pacinho.adventofcode2023.challange.day8.model.Side;
import pl.pacinho.adventofcode2023.challange.day8.tools.NavigationTools;
import pl.pacinho.adventofcode2023.challange.day8.tools.NetworkParser;
import pl.pacinho.adventofcode2023.model.Pair;
import pl.pacinho.adventofcode2023.utils.FileUtils;
import pl.pacinho.adventofcode2023.utils.MathUtils;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class Day8Part2 implements CalculateI {
    private Side[] navigate;
    private Map<String, Pair<String, String>> networkMap;

    @Override
    public long calculate(String filePath) {
        List<String> lines = FileUtils.readTxt(new File(filePath));

        navigate = NetworkParser.parseNavigate(lines);
        networkMap = NetworkParser.parseNetworkMap(lines);

        List<String> startNodes = networkMap.keySet()
                .stream()
                .filter(nodeName -> nodeName.endsWith("A"))
                .toList();

        long counter = 0L;
        int navigationIdx = 0;

        Map<String, Long> finalCounter = new HashMap<>();

        while (finalCounter.size() < startNodes.size()) {
            Side side = navigate[navigationIdx];
            List<String> newNodes = startNodes.stream()
                    .map(node -> side.applyFunc(networkMap.get(node)))
                    .toList();

            counter++;
            navigationIdx = NavigationTools.nextNavigationIdx(navigate.length, navigationIdx);
            startNodes = newNodes;

            List<String> finalNodes = newNodes.stream().filter(node -> node.trim().endsWith("Z")).toList();
            long finalCounter1 = counter;
            finalNodes.forEach(
                    node -> finalCounter.putIfAbsent(node, finalCounter1)
            );
        }

        return calculateLCM(finalCounter.values());
    }

    private long calculateLCM(Collection<Long> values) {
        return MathUtils.lcm(values.toArray(Long[]::new));
    }

    public static void main(String[] args) {
        System.out.println(
                new Day8Part2().calculate("challenges\\day8\\input.txt")
        );
    }
}
