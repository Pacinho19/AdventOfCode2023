package pl.pacinho.adventofcode2023.challange.day11;

import junit.framework.TestCase;

public class Day11Part1Test extends TestCase {

    public void testCalculate_BFS() {
        assertEquals(374L, new Day11Part1_BFS().calculate("challenges\\day11\\example.txt"));
    }

    public void testCalculateDijkstry() {
        assertEquals(374L, new Day11Part1_Dijkstry().calculate("challenges\\day11\\example.txt"));
    }

    public void testCalculate() {
        assertEquals(374L, new Day11Part1(2).calculate("challenges\\day11\\example.txt"));
    }

}