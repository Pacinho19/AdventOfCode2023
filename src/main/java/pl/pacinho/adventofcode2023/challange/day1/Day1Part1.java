package pl.pacinho.adventofcode2023.challange.day1;

import pl.pacinho.adventofcode2023.CalculateI;

public class Day1Part1 implements CalculateI {
    @Override
    public long calculate(String filePath) {
        return 0L;
    }

    public static void main(String[] args) {
        System.out.println(
                new Day1Part1().calculate("challenges\\day1\\input.txt")
        );
    }
}