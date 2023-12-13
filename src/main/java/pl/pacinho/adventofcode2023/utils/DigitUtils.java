package pl.pacinho.adventofcode2023.utils;

import java.util.Arrays;

public class DigitUtils {
    public static boolean isDigit(String value) {
        try {
            Long.parseLong(value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static Integer[] decimalToBinary(int num, int places) {
        String s = Integer.toBinaryString(num);
        while (s.length() < places) {
            s = "0" + s;
        }
        return Arrays.stream(s.split(""))
                .map(Integer::parseInt)
                .toArray(Integer[]::new);
    }

}