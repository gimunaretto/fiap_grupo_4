package fiap.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class UtilsBot {

    public static String getData() {
        return new SimpleDateFormat("dd/MM/yyyy").format(new Date());
    }

    public static String getHora() {
        return new SimpleDateFormat("HH:mm:ss").format(new Date());
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException,
            JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is,
                    Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    public static String sendWeather(String chatId, String textoMensagem) {
        JSONObject dadosTemperatura = null;
        Double temperatura = null;
        Object cidade = null;
        String resposta = null;

        try {

            JSONObject temperaturaAtual = readJsonFromUrl(
                    "http://api.openweathermap.org/data/2.5/weather?q=zuerich&mode=json&units=metric&cnt=7&appid=1bbd858dca78c0ee3438a910b6e23fb4");

            dadosTemperatura = temperaturaAtual.getJSONObject("main");
            temperatura = dadosTemperatura.getDouble("temp");
            cidade = temperaturaAtual.getString("name");

            return resposta = "Ótima pergunta! A temperatura atual na cidade de " + cidade + " é: "
                    + temperatura
                    + "°";

        } catch (Exception e) {

            return resposta = "Desculpe, não consigo te informar isso agora :(";

        }
    }

    static SendMessage responseBot(Update update) {
        String textoMensagem = update.getMessage().getText().toLowerCase();
        String chatId = update.getMessage().getChatId().toString();
        String resposta = "";

        if (textoMensagem.matches("\\b(?:ola|olá|oi|start)\\b")) {
            resposta = "\u2601 Olá, eu sou a WENDY. Estou aqui para lhe auxiliar a entender o sentido da vida! "
                    + "Pode me perguntar sobre o clima, o dia, filmes em cartaz no cinema e até um pouco sobre mim se quiser! :3";

        } else if (textoMensagem.matches("\\b(?:tempo|clima|temperatura)\\b")) {
            resposta = sendWeather(chatId, textoMensagem);

        } else {
            resposta = "Não entendi!\nPoderia repetir a pergunta novamente?";
        }

        return SendMessage.builder()
                .text(resposta)
                .chatId(chatId)
                .build();

    }

}
