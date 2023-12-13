package pl.pacinho.adventofcode2023.challange.day11.model;

public record PositionDto(int row, int col, String sign) {

    public String getPosAsString() {
        return row + "," + col;
    }
}
