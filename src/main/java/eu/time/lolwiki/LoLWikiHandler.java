package eu.time.lolwiki;

import java.io.IOException;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import eu.time.lua.Lexer;
import eu.time.lua.Parser;

public class LoLWikiHandler {
    private static final String CHAMPION_TABLE_URL = "https://leagueoflegends.fandom.com/wiki/Module:ChampionData/data?action=edit";

    private String tableData = getTableData();

    public Map<Double, AramChange> getChanges() {
        return new Parser(new Lexer(tableData)).parse();
    }

    private String getTableData() {
        Document doc = null;
        try {
            doc = Jsoup.connect(CHAMPION_TABLE_URL).get();
            Elements select = doc.select("#wpTextbox1");
            String text = select.get(0).text();
            return text.replace("-- </pre>\n-- [[Category:Lua]]", "").replace("-- <pre>\nreturn ","");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
