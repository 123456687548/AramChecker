package lcu;

import java.io.IOException;
import java.time.LocalDateTime;

import com.stirante.lolclient.ApiResponse;
import com.stirante.lolclient.ClientApi;
import com.stirante.lolclient.ClientConnectionListener;

import ChampSelectSession.Session;

public class LCUHandler {
    private final ClientApi api;

    private boolean ready = false;

    public LCUHandler() {
        this.api = new ClientApi();
        registerListener();
    }

    public Session getSession() throws IOException {
        if (!ready) {
            return null;
        }

        ApiResponse<Session> sessionResponse = api.executeGet("/lol-champ-select/v1/session", Session.class);
        
        if(!sessionResponse.isOk()){
            return null;
        }

        System.out.printf("%s : %s%n", LocalDateTime.now(), sessionResponse.getRawResponse());

        return sessionResponse.getResponseObject();
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
                        ready = true;
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
