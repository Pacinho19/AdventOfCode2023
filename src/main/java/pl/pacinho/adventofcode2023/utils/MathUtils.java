package pl.pacinho.adventofcode2023.utils;

public class MathUtils {

    public static long gcd(long a, long b) {
        while (b > 0) {
            long temp = b;
            b = a % b; // % is remainder
            a = temp;
        }
        return a;
    }


    public static long lcm(long a, long b) {
        return a * (b / gcd(a, b));
    }

    public static long lcm(Long[] input) {
        long result = input[0];
        for (int i = 1; i < input.length; i++) result = lcm(result, input[i]);
        return result;
    }

}
