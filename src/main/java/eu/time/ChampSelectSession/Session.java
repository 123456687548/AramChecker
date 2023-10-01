package eu.time.ChampSelectSession;

import java.util.ArrayList;
import java.util.List;

import generated.LolSummonerSummoner;

public class Session {
    public List<Object> actions;
    public boolean allowBattleBoost;
    public boolean allowDuplicatePicks;
    public boolean allowLockedEvents;
    public boolean allowRerolling;
    public boolean allowSkinSelection;
    public Bans bans;
    public List<BenchChampion> benchChampions;
    public boolean benchEnabled;
    public int boostableSkinCount;
    public ChatDetails chatDetails;
    public int counter;
    public EntitledFeatureState entitledFeatureState;
    public long gameId;
    public boolean hasSimultaneousBans;
    public boolean hasSimultaneousPicks;
    public boolean isCustomGame;
    public boolean isSpectating;
    public int localPlayerCellId;
    public int lockedEventIndex;
    public List<Team> myTeam;
    public List<Object> pickOrderSwaps;
    public int recoveryCounter;
    public int rerollsRemaining;
    public boolean skipChampionSelect;
    public List<Team> theirTeam;
    public Timer timer;
    public List<Trade> trades;

    public List<Champion> getAvailableChampions(LolSummonerSummoner me) {
        List<Champion> result = new ArrayList<>();

        if (benchEnabled) {
            benchChampions.forEach(champ -> result.add(Champion.createBenched(champ.championId)));
        }

        myTeam.forEach(team -> {
            if(team.championId == 0) return;
            
            if (me.summonerId.equals(team.summonerId)) {
                result.add(Champion.createSelected(team.championId));
            } else {
                result.add(Champion.createTeamSelected(team.championId));
            }
        });

        return result;
    }
}
