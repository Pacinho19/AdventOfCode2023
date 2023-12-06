package pl.pacinho.adventofcode2023.challange.day6.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public final class RaceDto {
    private long time;
    private long distance;
}
