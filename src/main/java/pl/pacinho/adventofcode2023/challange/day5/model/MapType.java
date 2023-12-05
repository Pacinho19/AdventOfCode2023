package pl.pacinho.adventofcode2023.challange.day5.model;

import java.util.Arrays;

public enum MapType {

    SEEDS(-1, "seeds:"),
    SOIL(1, "seed-to-soil"),
    FERTILIZER(2, "soil-to-fertilizer"),
    WATER(3, "fertilizer-to-water"),
    LIGHT(4, "water-to-light"),
    TEMPERATURE(5, "light-to-temperature"),
    HUMIDITY(6, "temperature-to-humidity"),
    LOCATION(7, "humidity-to-location");

    private final String text;
    private final int index;

    MapType(int index, String text) {
        this.index = index;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public int getIndex() {
        return index;
    }

    public static MapType fromText(String text) {
        return Arrays.stream(MapType.values())
                .filter(mt -> mt.getText().equals(text))
                .findFirst()
                .orElse(null);
    }
}
