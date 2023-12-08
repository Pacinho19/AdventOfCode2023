package pl.pacinho.adventofcode2023.challange.day8.tools;

import pl.pacinho.adventofcode2023.challange.day8.model.Side;
import pl.pacinho.adventofcode2023.model.Pair;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class NetworkParser {

    public static Side[] parseNavigate(List<String> lines) {
        return Arrays.stream(lines.get(0).split(""))
                .map(Side::getSide)
                .toArray(Side[]::new);
    }

    public static Map<String, Pair<String, String>> parseNetworkMap(List<String> lines) {
        return lines.stream()
                .skip(1)
                .filter(line -> !line.isEmpty())
                .collect(Collectors.toMap(line -> line.split("=")[0].trim(), NetworkParser::parseNavigate));
    }

    private static Pair<String, String> parseNavigate(String line) {
        String[] split = line.split("=")[1].trim()
                .replace("(", "")
                .replace(")", "")
                .split(",");
        return new Pair<>(split[0].trim(), split[1].trim());
    }

}
