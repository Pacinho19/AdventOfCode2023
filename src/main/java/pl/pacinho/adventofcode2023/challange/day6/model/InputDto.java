package pl.pacinho.adventofcode2023.challange.day6.model;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;

@Setter
@Getter
public class InputDto{
    private  LinkedList<Long> times;
    private  LinkedList<Long> distances;
}
