package pl.pacinho.adventofcode2023.challange.day8;

import junit.framework.TestCase;

public class Day8Part1Test extends TestCase {

    public void testCalculate() {
        assertEquals(2L, new Day8Part1().calculate("challenges\\day8\\example.txt"));
    }

    public void testCalculate2() {
        assertEquals(6L, new Day8Part1().calculate("challenges\\day8\\example2.txt"));
    }


}