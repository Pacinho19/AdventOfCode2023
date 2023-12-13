package pl.pacinho.adventofcode2023.challange.day11;

import junit.framework.TestCase;

public class Day11Part2Test extends TestCase {
    public void testCalculate() {
        assertEquals(1030L, new Day11Part2(10).calculate("challenges\\day11\\example.txt"));
    }
    public void testCalculate2() {
        assertEquals(8410L, new Day11Part2(100).calculate("challenges\\day11\\example.txt"));
    }

    public void testCalculate3() {
        assertEquals(374L, new Day11Part2(2).calculate("challenges\\day11\\example.txt"));
    }

}