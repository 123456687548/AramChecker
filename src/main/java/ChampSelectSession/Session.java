package ChampSelectSession;

import java.util.List;

public class Session {
    public List<Object> actions;
    public boolean allowBattleBoost;
    public boolean allowDuplicatePicks;
    public boolean allowLockedEvents;
    public boolean allowRerolling;
    public boolean allowSkinSelection;
    public Bans bans;
    public List<Object> benchChampions;
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
    public List<MyTeam> myTeam;
    public List<Object> pickOrderSwaps;
    public int recoveryCounter;
    public int rerollsRemaining;
    public boolean skipChampionSelect;
    public List<Object> theirTeam;
    public Timer timer;
    public List<Object> trades;
}
