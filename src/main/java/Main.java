import java.io.IOException;
import java.util.List;

import ChampSelectSession.Champion;
import ChampSelectSession.Session;
import lcu.LCUHandler;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        LCUHandler lcuHandler = new LCUHandler();

        while (true) {
            Session session = lcuHandler.getSession();

            if (session != null) {
                List<Champion> availableChampions = session.getAvailableChampions(lcuHandler.getMe());
                System.out.printf("");
            }

            Thread.sleep(5000);
        }
    }
}
