package eu.time;

import static org.fusesource.jansi.Ansi.ansi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.fusesource.jansi.AnsiConsole;

import eu.time.ChampSelectSession.Champion;
import eu.time.ChampSelectSession.Session;
import eu.time.lcu.LCUHandler;
import eu.time.lolwiki.AramChange;
import eu.time.lolwiki.LoLWikiHandler;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        AnsiConsole.systemInstall();
        LCUHandler lcuHandler = new LCUHandler();
        LoLWikiHandler loLWikiHandler = new LoLWikiHandler();
        Map<Double, AramChange> aramChanges = loLWikiHandler.getChanges();
        List<Champion> lastAvailableChampions = new ArrayList<>();

        while (true) {
            Session session = lcuHandler.getSession();

            if (session != null) {

                List<Champion> availableChampions = session.getAvailableChampions(lcuHandler.getMe());

                if (!lastAvailableChampions.containsAll(availableChampions)) {
                    clearConsole();
                    for (Champion availableChampion : availableChampions) {
                        availableChampion.printInfo(aramChanges.get((double) availableChampion.championId));
                    }
                }

                lastAvailableChampions = availableChampions;
            } else {
                lastAvailableChampions = new ArrayList<>();
                clearConsole();
            }

            Thread.sleep(2000);
        }
    }

    private static void clearConsole() {
        System.out.println(ansi().eraseScreen());
    }
}
