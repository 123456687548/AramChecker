package eu.time.aramchecker.lua;

import java.util.HashMap;
import java.util.Map;

import eu.time.aramchecker.lolwiki.AramChange;

public class Parser {
    private Lexer lexer;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
    }

    public Map<Double, AramChange> parse() {
        Map<Double, AramChange> changes = new HashMap<>();
        Token token = lexer.peek();
        while (true) {
            if (token instanceof Token.EOF) {
                return changes;
            }

            AramChange nextAramChange = getNextAramChange();
            if (nextAramChange != null) {
                changes.put(nextAramChange.id, nextAramChange);
            }
            if (!findNextChampion()) { // no more champions in list
                return changes;
            }
        }
    }

    private AramChange getNextAramChange() {
        String currentChampionName = null;
        Double currentChampionId = null;
        AramChange aramChange = new AramChange();

        while (true) {
            Token peek = lexer.peek();

            if (peek instanceof Token.EOF) {
                throw new RuntimeException("EOF reached while getting next aram change!");
            }

            if (peek instanceof Token.TString tString) {
                switch (tString.value) {
                    case "id" -> {
                        if (currentChampionId == null) {
                            currentChampionId = handleChampionId();
                            aramChange.id = currentChampionId;
                        } else {
                            return aramChange;
                        }
                    }
                    case "apiname" -> {
                        if (currentChampionName == null) {
                            currentChampionName = handleChampionName();
                            aramChange.name = currentChampionName;
                        } else {
                            return aramChange;
                        }
                    }
                    case "aram" -> {
                        aramChange = handleAramChange();
                        aramChange.id = currentChampionId;
                        aramChange.name = currentChampionName;
                        return aramChange;
                    }
                }
            }
            lexer.next();
        }
    }

    private boolean findNextChampion() {
        while (true) {
            Token peek = lexer.peek();

            if (peek instanceof Token.EOF) {
                return false;
            }

            if (peek instanceof Token.TString tString) {
                if (tString.value.equals("id")) {
                    return true;
                }
            }
            lexer.next();
        }
    }

    private AramChange handleAramChange() {
        AramChange aramChange = new AramChange();

        while (true) {
            Token token = lexer.peek();
            if (token instanceof Token.RCURLY) {
                lexer.next();
                return aramChange;
            }

            Token next = lexer.next();

            if (next instanceof Token.TString tString) {
                Token nextAssignValue = getNextAssignValue();

                if (nextAssignValue instanceof Token.TNumber tNumber) {
                    switch (tString.value) {
                        case "ability_haste" -> aramChange.ability_haste = Double.parseDouble(tNumber.value);
                        case "dmg_dealt" -> aramChange.dmg_dealt = Double.parseDouble(tNumber.value);
                        case "dmg_taken" -> aramChange.dmg_taken = Double.parseDouble(tNumber.value);
                        case "healing" -> aramChange.healing = Double.parseDouble(tNumber.value);
                        case "shielding" -> aramChange.shielding = Double.parseDouble(tNumber.value);
                        case "tenacity" -> aramChange.tenacity = Double.parseDouble(tNumber.value);
                        case "energy_regen" -> aramChange.energy_regen = Double.parseDouble(tNumber.value);
                        case "attack_speed" -> aramChange.attack_speed = Double.parseDouble(tNumber.value);
                        default -> System.out.printf("Unknown aram change value %s%n", tString.value);
                    }
                }
            }
        }
    }

    private String handleChampionName() {
        Token nextAssignValue = getNextAssignValue();

        if (nextAssignValue instanceof Token.TString assignValue) {
            return assignValue.value;
        }
        throw new RuntimeException("currentChampionName is not a string");
    }

    private double handleChampionId() {
        Token nextAssignValue = getNextAssignValue();

        if (nextAssignValue instanceof Token.TNumber assignValue) {
            return Double.parseDouble(assignValue.value);
        }
        throw new RuntimeException("currentChampionId is not a number");
    }

    private Token getNextAssignValue() {
        while (true) {
            Token next = lexer.next();
            if (next instanceof Token.ASSIGN) {
                next = lexer.next();
                return next;
            }
        }
    }

    public class ParseException extends Exception {
    }
}