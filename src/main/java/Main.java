import java.io.IOException;

import com.stirante.lolclient.libs.com.google.gson.Gson;

import ChampSelectSession.Session;
import lcu.LCUHandler;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        LCUHandler lcuHandler = new LCUHandler();

        while (true) {
            Session session = lcuHandler.getSession();

            if (session != null) {
                System.out.printf("");
            }

            Thread.sleep(5000);
        }
    }
}
