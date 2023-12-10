package pl.pacinho.adventofcode2023.challange.day10.model;

import java.util.List;

public record Neighbor(NeighborType type, List<String> pipes) {
}