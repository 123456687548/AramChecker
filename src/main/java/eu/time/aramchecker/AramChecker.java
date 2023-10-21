package eu.time.aramchecker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.fusesource.jansi.AnsiConsole;

import eu.time.aramchecker.ChampSelectSession.Champion;
import eu.time.aramchecker.ChampSelectSession.Session;
import eu.time.aramchecker.console.ConsoleHandler;
import eu.time.aramchecker.console.PrintAbleInGameData;
import eu.time.aramchecker.datadragon.DataDragonHandler;
import eu.time.aramchecker.lcd.LiveClientDataHandler;
import eu.time.aramchecker.lcd.pojo.Player;
import eu.time.aramchecker.lcd.pojo.LiveData;
import eu.time.aramchecker.lcu.LCUHandler;
import eu.time.aramchecker.lolwiki.AramChange;
import eu.time.aramchecker.console.PrintAbleChampion;
import eu.time.aramchecker.lolwiki.LoLWikiHandler;
import generated.LolSummonerSummoner;

public class AramChecker {
    private final DataDragonHandler dataDragonHandler = new DataDragonHandler();
    private final LoLWikiHandler loLWikiHandler = new LoLWikiHandler();
    private final ConsoleHandler consoleHandler = new ConsoleHandler();
    private final LCUHandler lcuHandler = new LCUHandler();
    private final LiveClientDataHandler liveClientDataHandler = new LiveClientDataHandler();

    private List<Champion> lastAvailableChampions = new ArrayList<>();
    private Map<Double, AramChange> aramChanges;

    private boolean needClear = false;
    private boolean printedIngame = false;

    public static void main(String[] args) throws InterruptedException {
        AnsiConsole.systemInstall();

        new AramChecker().loop();
    }

    @SuppressWarnings("java:S2189")
    public void loop() throws InterruptedException {
        aramChanges = loLWikiHandler.getChanges();

        while (true) {
            if (!lcuHandler.isReady()) {
                System.out.print("\rWaiting for League of Legends to start...");
                Thread.sleep(2000);
                continue;
            }

            Session session = lcuHandler.getSession();

            if (session == null || session.timer.phase.equals("GAME_STARTING") /*|| !session.benchEnabled*/) {
                LiveData liveGame = liveClientDataHandler.getLiveGame();
                if (liveGame != null) {
                    handleInGame(liveGame);
                } else {
                    waitingForChampSelect();
                }
                Thread.sleep(2000);
                continue;
            }

            printedIngame = false;

            LolSummonerSummoner me = lcuHandler.getMe();

            if (me == null) {
                System.out.println("\nCan't find own Summoner Profile... Retrying");
                Thread.sleep(2000);
                continue;
            }

            handleChampionSelect(session, me);

            Thread.sleep(1000);
        }
    }

    private void waitingForChampSelect() {
        lastAvailableChampions = new ArrayList<>();
        if (needClear) {
            consoleHandler.clearConsole();
            needClear = false;
        }
        System.out.print("\rWaiting for Aram Champ select...");
    }

    private void handleChampionSelect(Session session, LolSummonerSummoner me) {
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
    }

    private void handleInGame(LiveData liveGame) {
        if (printedIngame) {
            return;
        }

        if (needClear) {
            consoleHandler.clearConsole();
        }

        String me = liveGame.activePlayer.summonerName;
        String meTeam = "";

        List<PrintAbleInGameData> printAbleIngameData = new ArrayList<>();

        List<Player> teamOrder = new ArrayList<>();
        List<Player> teamChaos = new ArrayList<>();

        for (Player player : liveGame.allPlayers) {
            if (player.summonerName.equals(me)) {
                player.isLocal = true;
                meTeam = player.team;
            }
            switch (player.team) {
                case "ORDER" -> teamOrder.add(player);
                case "CHAOS" -> teamChaos.add(player);
            }
        }

        switch (meTeam) {
            case "ORDER" -> {
                addLivePlayersToPrint(teamOrder, printAbleIngameData, true);
                addLivePlayersToPrint(teamChaos, printAbleIngameData, false);
            }
            case "CHAOS" -> {
                addLivePlayersToPrint(teamChaos, printAbleIngameData, true);
                addLivePlayersToPrint(teamOrder, printAbleIngameData, false);
            }
        }

        consoleHandler.print(printAbleIngameData);
        printedIngame = true;
    }

    private void addLivePlayersToPrint(List<Player> players, List<PrintAbleInGameData> toPrint, boolean isAlly) {
        for (Player player : players) {
            eu.time.aramchecker.datadragon.Champion ddChampion = dataDragonHandler.getChampionNameMap().get(player.championName);
            AramChange aramChange = aramChanges.get(Double.parseDouble(ddChampion.key));
            aramChange.setName(player.championName);
            toPrint.add(new PrintAbleInGameData(player.summonerName, aramChange, player.isLocal, isAlly));
        }
    }
}
