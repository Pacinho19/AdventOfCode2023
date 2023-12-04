package pl.pacinho.adventofcode2023.challange.day4;

import pl.pacinho.adventofcode2023.CalculateI;
import pl.pacinho.adventofcode2023.challange.day4.model.CardDto;
import pl.pacinho.adventofcode2023.utils.FileUtils;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day4Part2 implements CalculateI {

    private Map<Integer, CardDto> cardsMap;
    private long winingCardsCount = 0;

    @Override
    public long calculate(String filePath) {
        cardsMap = FileUtils.readTxt(new File(filePath))
                .stream()
                .map(this::parseCard)
                .collect(Collectors.toMap(CardDto::idx, Function.identity()));

        cardsMap.forEach(this::checkCard);

        return winingCardsCount;
    }

    private void checkCard(Integer idx, CardDto cardDto) {
        winingCardsCount++;

        long count = cardDto.userNumbers()
                .stream()
                .filter(number -> cardDto.winingNumbers().contains(number))
                .count();

        if (count == 0)
            return;

        IntStream.rangeClosed(idx + 1, (int) (idx + count))
                .boxed()
                .forEach(i -> checkCard(i, cardsMap.get(i)));
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
                new Day4Part2().calculate("challenges\\day4\\input.txt")
        );
    }
}