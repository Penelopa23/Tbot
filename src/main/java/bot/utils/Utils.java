package bot.utils;

import lombok.SneakyThrows;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class Utils {

    /**
     * Запращиваем ссылку для пользователя
     */
    @SneakyThrows
    public static String getUrl(String mainUrl) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String result = "";
        try {

            // создаем объект клиента
            HttpGet request = new HttpGet(mainUrl);
            CloseableHttpResponse response = httpClient.execute(request);

            try {
                // получаем статус ответа
                System.out.println(response.getProtocolVersion());              // HTTP/1.1
                System.out.println(response.getStatusLine().getStatusCode());   // 200
                System.out.println(response.getStatusLine().getReasonPhrase()); // OK
                System.out.println(response.getStatusLine().toString());        // HTTP/1.1 200 OK

                HttpEntity entity = response.getEntity();
                // если есть тело ответа
                if (entity != null) {
                    // возвращаем строку
                    result = EntityUtils.toString(entity);
                    System.out.println(result);
                }

            } finally {
                // закрываем соединения
                response.close();
            }
        } finally {
            httpClient.close();
        }
        return result;
    }
}
