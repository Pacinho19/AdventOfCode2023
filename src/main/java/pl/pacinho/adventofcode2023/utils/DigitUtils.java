package pl.pacinho.adventofcode2023.utils;

public class DigitUtils {
    public static boolean isDigit(String value) {
        try {
            Long.parseLong(value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
