package eu.time.aramchecker.lcu;

import java.io.IOException;

import com.stirante.lolclient.ApiResponse;
import com.stirante.lolclient.ClientApi;
import com.stirante.lolclient.ClientConnectionListener;

import eu.time.aramchecker.ChampSelectSession.Session;
import generated.LolSummonerSummoner;

public class LCUHandler {
    private final ClientApi api;
    private boolean ready = false;

    private LolSummonerSummoner me;

    public LCUHandler() {
        this.api = new ClientApi();
        registerListener();
    }

    public Session getSession() throws IOException {
        if (!ready) {
            return null;
        }

        ApiResponse<Session> sessionResponse = api.executeGet("/lol-champ-select/v1/session", Session.class);

        if (!sessionResponse.isOk()) {
            return null;
        }

//        System.out.printf("%s : %s%n", LocalDateTime.now(), sessionResponse.getRawResponse());

        return sessionResponse.getResponseObject();
    }

    public LolSummonerSummoner getMe() {
        return me;
    }

    private void loadMe() throws IOException {
        me = api.executeGet("/lol-summoner/v1/current-summoner", LolSummonerSummoner.class).getResponseObject();
    }

    private void registerListener() {
        api.addClientConnectionListener(new ClientConnectionListener() {
            @Override
            public void onClientConnected() {
                while (!ready) {
                    try {
                        if (!api.isAuthorized()) {
                            System.out.println("Not logged in!");
                            Thread.sleep(1000);
                        }
                        loadMe();
                        ready = true;
                        System.out.println("logged in!");
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onClientDisconnected() {
                //                api.stop();
            }
        });
    }
}
