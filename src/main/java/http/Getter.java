package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;

public class Getter {
    public static <T> T get(String sUrl, Class<T> clazz) throws IOException {
        Gson gson = new Gson();

        String result = get(sUrl);

        return gson.fromJson(result, clazz);
    }

    public static String get(String sUrl) throws IOException {
        URL url = new URL(sUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(
            new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();

        return content.toString();
    }
}
