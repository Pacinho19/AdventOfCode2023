package pl.pacinho.adventofcode2023.challange.day2.model;

import java.util.List;
import java.util.Map;

public record GameDto(long idx, List<Map<String, Integer>> sets) {
}