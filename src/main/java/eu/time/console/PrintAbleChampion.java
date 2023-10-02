package eu.time.console;

import eu.time.ChampSelectSession.Champion;
import eu.time.lolwiki.AramChange;

public class PrintAbleChampion {
    private Champion champion;
    private AramChange aramChange;

    public PrintAbleChampion(Champion champion, AramChange aramChange) {
        this.champion = champion;
        this.aramChange = aramChange;
    }
    
    public String getPrintString(){
        return champion.getPrintString(aramChange);
    }
}
