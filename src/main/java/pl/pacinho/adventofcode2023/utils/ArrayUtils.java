package pl.pacinho.adventofcode2023.utils;

public class ArrayUtils {
    public static void print2DArray(Object[][] array) {
        for (Object[] x : array) {
            for (Object y : x) {
                System.out.print(y + " ");
            }
            System.out.println();
        }
    }
}
