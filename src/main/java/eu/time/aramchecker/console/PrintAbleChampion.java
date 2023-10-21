package eu.time.aramchecker.console;

import static org.fusesource.jansi.Ansi.ansi;

import eu.time.aramchecker.ChampSelectSession.Champion;
import eu.time.aramchecker.lolwiki.AramChange;

public class PrintAbleChampion extends AbstactPrintAble {
    private static final int SELECTION_WIDTH = 10;
    private static final int CHAMPION_NAME_WIDTH = 14;
    
    private Champion champion;
    private AramChange aramChange;

    public PrintAbleChampion(Champion champion, AramChange aramChange) {
        this.champion = champion;
        this.aramChange = aramChange;
    }

    public String getPrintString() {
        String availability = "NO AVAILABILITY?!";

        if (champion.isSelected) {
            availability = String.format("@|green %-" + SELECTION_WIDTH + "s|@", "SELECTED");
        } else if (champion.isBenched) {
            availability = String.format("@|yellow %-" + SELECTION_WIDTH + "s|@", "BENCHED");
        } else if (champion.isTeamSelected) {
            availability = String.format("@|red %-" + SELECTION_WIDTH + "s|@", "TRADE");
        }

        return ansi().render(
            String.format("   %-" + CHAMPION_NAME_WIDTH + "s %s %n%s%n",
                aramChange.getName(),
                availability,
                aramChange.getPrintString(getMaxWidth())
            )
        ).toString();
    }

    @Override
    int getMaxWidth() {
        return 5 + SELECTION_WIDTH + CHAMPION_NAME_WIDTH;
    }
}
