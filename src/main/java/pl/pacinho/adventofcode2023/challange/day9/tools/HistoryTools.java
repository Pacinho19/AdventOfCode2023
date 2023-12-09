package pl.pacinho.adventofcode2023.challange.day9.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class HistoryTools {

    public static List<Long[]> parseSignalHistory(String s) {
        Long[] arr = parseNumbersArray(s.split(" "));
        List<Long[]> out = new ArrayList<>();
        out.add(arr);

        while (!isAllValuesZero(out)) {
            Long[] lastRecord = getLastRecord(out);
            Long[] newArr = IntStream.range(0, lastRecord.length - 1)
                    .boxed()
                    .map(i -> lastRecord[i + 1] - lastRecord[i])
                    .toArray(Long[]::new);

            out.add(newArr);
        }
        return out;
    }

    public static Long[] getLastRecord(List<Long[]> out) {
        return out.get(out.size() - 1);
    }

    public static Long[] parseNumbersArray(String[] s) {
        return Arrays.stream(s)
                .map(Long::parseLong)
                .toArray(Long[]::new);
    }

    private static boolean isAllValuesZero(List<Long[]> out) {
        Long[] lastRecord = getLastRecord(out);
        return Arrays.stream(lastRecord)
                .allMatch(l -> l == 0L);
    }


}