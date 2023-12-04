package pl.pacinho.adventofcode2023.challange.day4;

import pl.pacinho.adventofcode2023.CalculateI;
import pl.pacinho.adventofcode2023.challange.day4.model.CardDto;
import pl.pacinho.adventofcode2023.utils.FileUtils;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class Day4Part1 implements CalculateI {

    @Override
    public long calculate(String filePath) {
        return FileUtils.readTxt(new File(filePath))
                .stream()
                .map(this::parseCard)
                .map(this::checkCard)
                .reduce(0L, Long::sum);
    }

    private long checkCard(CardDto cardDto) {
        long count = cardDto.userNumbers()
                .stream()
                .filter(number -> cardDto.winingNumbers().contains(number))
                .count();

        return count == 1
                ? count
                : (long) Math.pow(2, count - 1);
    }

    private CardDto parseCard(String s) {
        int idx = Integer.parseInt(s.split(":")[0].split("Card")[1].trim());
        return new CardDto(idx, parseNumbers(s.split(":")[1].split("\\|")[0]), parseNumbers(s.split("\\|")[1]));
    }

    private List<Integer> parseNumbers(String s) {
        return Arrays.stream(s.trim().split(" "))
                .filter(s1 -> !s1.isEmpty())
                .map(Integer::parseInt)
                .toList();
    }

    public static void main(String[] args) {
        System.out.println(
                new Day4Part1().calculate("challenges\\day4\\input.txt")
        );
    }
}