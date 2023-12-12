package pl.pacinho.adventofcode2023.challange.day10;

import junit.framework.TestCase;

public class Day10Part2Test extends TestCase {

    public void testCalculate() {
        assertEquals(4L, new Day10Part2().calculate("challenges\\day10\\example3.txt"));
    }

    public void testCalculate2() {
        assertEquals(8L, new Day10Part2().calculate("challenges\\day10\\example4.txt"));
    }
    public void testCalculate3() {
        assertEquals(10L, new Day10Part2().calculate("challenges\\day10\\example5.txt"));
    }


}