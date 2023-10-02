package eu.time.aramchecker.console;

import static org.fusesource.jansi.Ansi.ansi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ConsoleHandler {
    private static final int MAX_COLUMNS = 4;

    public void print(List<PrintAbleChampion> toPrint) {
        while (!toPrint.isEmpty()) {
            List<PrintAbleChampion> printBatch = getNextBatchToPrint(toPrint);
            int lineCount = getRequiredLineCount(printBatch);
            StringBuilder[] lineBuilders = createLineBuilder(lineCount);

            for (PrintAbleChampion printAbleChampion : printBatch) {
                String printString = printAbleChampion.getPrintString();
                String[] lines = printString.split("\r\n");

                for (int i = 0; i < lines.length; i++) {
                    lineBuilders[i].append("|   ").append(lines[i]);
                }
            }

            for (StringBuilder lineBuilder : lineBuilders) {
                lineBuilder.append("|");
                System.out.println(lineBuilder);
            }

            int maxLineLength = getMinLineLength(lineBuilders);

            StringBuilder bottomLine = new StringBuilder(maxLineLength);
            bottomLine.append(String.join("", Collections.nCopies(maxLineLength, "-")));
            System.out.println(bottomLine);
        }
    }

    private List<PrintAbleChampion> getNextBatchToPrint(List<PrintAbleChampion> toPrint) {
        List<PrintAbleChampion> batch = new ArrayList<>();

        for (int i = 0; i < MAX_COLUMNS; i++) {
            if (!toPrint.isEmpty()) {
                batch.add(toPrint.remove(0));
            }
        }

        return batch;
    }

    private int getRequiredLineCount(List<PrintAbleChampion> toPrint) {
        int requiredLines = 0;

        for (PrintAbleChampion printAbleChampion : toPrint) {
            String printString = printAbleChampion.getPrintString();
            String[] lines = printString.split("\r\n");
            requiredLines = Math.max(requiredLines, lines.length);
        }

        return requiredLines;
    }

    private int getMinLineLength(StringBuilder[] lines) {
        int maxLength = 99999;

        for (StringBuilder line : lines) {
            maxLength = Math.min(maxLength, line.length());
        }

        return maxLength;
    }

    private StringBuilder[] createLineBuilder(int lines) {
        StringBuilder[] lineBuilders = new StringBuilder[lines];
        for (int i = 0; i < lineBuilders.length; i++) {
            lineBuilders[i] = new StringBuilder();
        }

        return lineBuilders;
    }

    public void clearConsole() {
        System.out.print(ansi().eraseScreen().cursor(1,1));
    }
}
