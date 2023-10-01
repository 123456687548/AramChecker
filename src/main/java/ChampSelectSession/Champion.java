package ChampSelectSession;

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
}
