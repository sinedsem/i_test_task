package ru.intterra;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.intterra.domain.User;
import ru.intterra.service.InputParser;
import ru.intterra.service.Merger;
import ru.intterra.service.OutputFormatter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    private final Merger merger = new Merger();
    private final InputParser inputParser = new InputParser();
    private final OutputFormatter outputFormatter = new OutputFormatter();

    public static void main(String[] args) {
        new Application().run();
    }

    private void run() {
        List<String> lines = readSystemIn();
        if (lines.isEmpty()) {
            return;
        }

        List<User> users = inputParser.parse(lines);
        List<User> result = merger.merge(users);

        PrintWriter printWriter = new PrintWriter(System.out);
        outputFormatter.format(result).forEach(printWriter::println);
        printWriter.close();
    }

    private List<String> readSystemIn() {
        List<String> lines = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String line;
            while ((line = reader.readLine()) != null && !line.isEmpty()) {
                lines.add(line);
            }
        } catch (IOException e) {
            log.error("Error while reading input", e);
        }
        return lines;
    }

}
