package bot.utils;

import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Utils {

    /**
     * Запращиваем ссылку для пользователя
     */
    @SneakyThrows
    public static String getUrl(String mainUrl) {
        URL obj = new URL(mainUrl);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

        connection.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }

    public static String[] splitInputText(String inputText) {
        String[] result = inputText.split("!");
        return result;
    }

}
