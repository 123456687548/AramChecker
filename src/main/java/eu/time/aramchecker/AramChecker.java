package eu.time.aramchecker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.fusesource.jansi.AnsiConsole;

import eu.time.aramchecker.ChampSelectSession.Champion;
import eu.time.aramchecker.ChampSelectSession.Session;
import eu.time.aramchecker.console.ConsoleHandler;
import eu.time.aramchecker.lcu.LCUHandler;
import eu.time.aramchecker.lolwiki.AramChange;
import eu.time.aramchecker.console.PrintAbleChampion;
import eu.time.aramchecker.lolwiki.LoLWikiHandler;
import generated.LolSummonerSummoner;

public class AramChecker {
    public static void main(String[] args) throws InterruptedException {
        AnsiConsole.systemInstall();
        LCUHandler lcuHandler = new LCUHandler();
        LoLWikiHandler loLWikiHandler = new LoLWikiHandler();
        ConsoleHandler consoleHandler = new ConsoleHandler();
        Map<Double, AramChange> aramChanges = loLWikiHandler.getChanges();
        List<Champion> lastAvailableChampions = new ArrayList<>();

        boolean needClear = false;

        while (true) {
            if (!lcuHandler.isReady()) {
                System.out.print("\rWaiting for League of Legends to start...");
                Thread.sleep(2000);
                continue;
            }

            Session session = lcuHandler.getSession();

            if (session == null || !session.benchEnabled) {
                lastAvailableChampions = new ArrayList<>();
                if (needClear) {
                    consoleHandler.clearConsole();
                    needClear = false;
                }
                System.out.print("\rWaiting for Aram Champ select...");
                Thread.sleep(2000);
                continue;
            }

            LolSummonerSummoner me = lcuHandler.getMe();
            
            if(me == null){
                System.out.println("\nCan't find own Summoner Profile... Retrying");
                Thread.sleep(2000);
                continue;
            }
            
            List<Champion> availableChampions = session.getAvailableChampions(me);
            List<PrintAbleChampion> printAbleChampions = new ArrayList<>();

            if (!lastAvailableChampions.containsAll(availableChampions)) {
                consoleHandler.clearConsole();
                for (Champion availableChampion : availableChampions) {
                    AramChange aramChange = aramChanges.get((double) availableChampion.championId);
                    printAbleChampions.add(new PrintAbleChampion(availableChampion, aramChange));
                }
                consoleHandler.print(printAbleChampions);
            }

            lastAvailableChampions = availableChampions;
            needClear = true;
            
            Thread.sleep(1000);
        }
    }
}
