package eu.time.aramchecker.ChampSelectSession;

import static org.fusesource.jansi.Ansi.ansi;

import java.util.Objects;

import eu.time.aramchecker.lolwiki.AramChange;

public class Champion {
    public int championId;
    public String championName;
    public boolean isBenched;
    public boolean isSelected;
    public boolean isTeamSelected;

    private Champion(int championId, String championName, boolean isBenched, boolean isSelected, boolean isTeamSelected) {
        this.championId = championId;
        this.championName = championName;
        this.isBenched = isBenched;
        this.isSelected = isSelected;
        this.isTeamSelected = isTeamSelected;
    }

    public static Champion createSelected(int championId) {
        String championName = getChampionName(championId);
        return new Champion(championId, championName, false, true, false);
    }

    public static Champion createBenched(int championId) {
        String championName = getChampionName(championId);
        return new Champion(championId, championName, true, false, false);
    }

    public static Champion createTeamSelected(int championId) {
        String championName = getChampionName(championId);
        return new Champion(championId, championName, false, false, true);
    }

    private static String getChampionName(int championId) {
        return "";
    }
    
    public String getPrintString(AramChange aramChange) {
        String availability = "NO AVAILABILITY?!";
        
        if (isSelected) {
            availability = String.format("@|green %-10s|@", "SELECTED");
        } else if (isBenched) {
            availability = String.format("@|yellow %-10s|@", "BENCHED");
        } else if (isTeamSelected) {
            availability = String.format("@|red %-10s|@","TRADE");
        }

        return ansi().render(String.format("%-14s %s %n%s%n",aramChange.getName(), availability , aramChange)).toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Champion champion = (Champion) o;
        return championId == champion.championId && isBenched == champion.isBenched && isSelected == champion.isSelected && isTeamSelected == champion.isTeamSelected;
    }

    @Override
    public int hashCode() {
        return Objects.hash(championId, isBenched, isSelected, isTeamSelected);
    }
}
