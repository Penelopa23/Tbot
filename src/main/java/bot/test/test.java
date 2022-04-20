//package bot.test;
//
//
//import bot.utils.Utils;
//import org.springframework.context.annotation.Bean;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//
//import java.io.UnsupportedEncodingException;
//import java.net.URLDecoder;
//import java.net.URLEncoder;
//import java.nio.charset.StandardCharsets;
//import java.util.ArrayList;
//
//@Service
//@EnableScheduling
//public class test {
//
//    @Scheduled(fixedRate = 200000)
//    public void execute() {
//        String chatId = "384799467";
//        ArrayList<String> l = new ArrayList<>();
//        l.add("Your are the best");
//        for(String s : l) {
//            String a = URLEncoder.encode(s, StandardCharsets.UTF_8);
//            String url = "https://api.telegram.org/bot1970922099:AAFgu3OzMmbM5QAE0ww_JRXkMVoQjaP10Hc/sendMessage?" +
//                    "chat_id=" + chatId + "&text=" + a;
//
//            Utils.getUrl(url);
//        }
//
//    }
//}
