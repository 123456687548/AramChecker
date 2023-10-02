package eu.time.aramchecker.console;

import eu.time.aramchecker.ChampSelectSession.Champion;
import eu.time.aramchecker.lolwiki.AramChange;

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
