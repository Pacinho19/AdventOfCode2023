package pl.pacinho.adventofcode2023.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileUtils {

    public static List<String> readTxt(File file) {
        try (Stream<String> stream = Files.lines(Paths.get(file.getAbsolutePath()))) {
            return stream.collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static String readAsText(File file) {
        try (Stream<String> stream = Files.lines(Paths.get(file.getAbsolutePath()))) {
            return stream.collect(Collectors.joining("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}