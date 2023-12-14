package pl.pacinho.adventofcode2023.challange.day12;

import junit.framework.TestCase;
import pl.pacinho.adventofcode2023.challange.day10.Day10Part1;

public class Day12Part1Test extends TestCase {

    public void testCalculate() {
        assertEquals(21L, new Day12Part1().calculate("challenges\\day12\\example.txt"));
    }
    public void testCalculate2() {
        assertEquals(16, new Day12Part1().calculate("challenges\\day12\\example2.txt"));
    }

}