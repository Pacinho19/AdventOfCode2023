package pl.pacinho.adventofcode2023.challange.day8.day10;

import junit.framework.TestCase;
import pl.pacinho.adventofcode2023.challange.day10.Day10Part1;

public class Day10Part1Test extends TestCase {

    public void testCalculate() {
        assertEquals(4L, new Day10Part1().calculate("challenges\\day10\\example.txt"));
    }

    public void testCalculate2() {
        assertEquals(8L, new Day10Part1().calculate("challenges\\day10\\example2.txt"));
    }


}