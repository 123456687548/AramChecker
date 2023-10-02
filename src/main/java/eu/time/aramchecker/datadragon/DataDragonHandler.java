package eu.time.aramchecker.datadragon;

import java.io.IOException;
import java.util.Map;

import eu.time.aramchecker.http.Getter;

public class DataDragonHandler {
    private static final String VERSION_JSON_URL = "https://ddragon.leagueoflegends.com/api/versions.json";
    private static final String CHAMPION_JSON_URL = "http://ddragon.leagueoflegends.com/cdn/{}/data/en_US/champion.json";

    private String[] versions = loadVersions();
    private Map<Integer, Champion> championMap = getChampions();

    public Map<Integer, Champion> getChampionMap() {
        return championMap;
    }

    private Map<Integer, Champion> getChampions() {
        String requestUrl = CHAMPION_JSON_URL.replace("{}", getCurrentVersion());

        try {
            DDChampions ddChampions = Getter.get(requestUrl, DDChampions.class);
            return ddChampions.getChampionList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getCurrentVersion() {
        assert versions.length > 0;

        return versions[0];
    }

    private String[] loadVersions() {
        String[] result;

        try {
            result = Getter.get(VERSION_JSON_URL, String[].class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return result;
    }
}
