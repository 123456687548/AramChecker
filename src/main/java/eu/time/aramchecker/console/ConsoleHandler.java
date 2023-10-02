package eu.time.aramchecker.console;

import java.util.Collections;
import java.util.List;

public class ConsoleHandler {
    public void print(List<PrintAbleChampion> toPrint) {
        int lineCount = 9;

        StringBuilder[] lineBuilders = new StringBuilder[lineCount];
        for (int i = 0; i < lineBuilders.length; i++) {
            lineBuilders[i] = new StringBuilder();
        }

        int column = 0;
        for (PrintAbleChampion printAbleChampion : toPrint) {
            String printString = printAbleChampion.getPrintString();
            String[] lines = printString.split("\r\n");

            if (column == 4) {
                int lineLength = 0;
                for (StringBuilder lineBuilder : lineBuilders) {
                    System.out.println(lineBuilder);
                    lineLength = lineBuilder.length() + 8;
                }
                StringBuilder bottomLine = new StringBuilder(lineLength);
                bottomLine.append(String.join("", Collections.nCopies(lineLength, "-")));
                System.out.println(bottomLine);

                for (int i = 0; i < lineBuilders.length; i++) {
                    lineBuilders[i] = new StringBuilder();
                }
                column = 0;
            }

            for (int i = 0; i < lines.length; i++) {
                lineBuilders[i].append(lines[i]).append("|\t");
            }

            column++;
        }

        for (StringBuilder lineBuilder : lineBuilders) {
            System.out.println(lineBuilder);
        }
    }
}
