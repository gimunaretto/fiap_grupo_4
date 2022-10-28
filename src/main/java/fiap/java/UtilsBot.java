package fiap.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class UtilsBot {

    /**
     * @return
     */
    public static String getData() {
        LocalDate dataHoje = LocalDate.now();
        return dataHoje.getDayOfMonth() + " de " +
                dataHoje.getMonth().getDisplayName(TextStyle.FULL, new Locale("pt")) +
                " de " +
                dataHoje.getYear() + ".";
    }

    /**
     * @return
     */
    public static String getHora() {
        return new SimpleDateFormat("HH:mm:ss").format(new Date());
    }

    /**
     * @param update
     * @return
     */
    public static String getDiaSemana() {
        return LocalDate.now().getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("pt"));
    }

    /**
     * @param rd
     * @return
     * @throws IOException
     */
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    /**
     * @param url
     * @return
     * @throws IOException
     * @throws JSONException
     */
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

    /**
     * @return
     */
    public static String sendWeather() {
        JSONObject dadosTemperatura = null;
        Double temperatura = null;
        Object ipCidade = null;
        Object cidade = null;

        try {
            JSONObject enderecoIP = readJsonFromUrl("https://ipinfo.io/json");

            ipCidade = enderecoIP.getString("city");

            JSONObject temperaturaAtual = readJsonFromUrl(
                    "http://api.openweathermap.org/data/2.5/weather?q=" + ipCidade
                            + "&mode=json&units=metric&cnt=7&appid=1bbd858dca78c0ee3438a910b6e23fb4&lang=pt_br");

            dadosTemperatura = temperaturaAtual.getJSONObject("main");
            temperatura = dadosTemperatura.getDouble("temp");
            cidade = temperaturaAtual.getString("name");

            return "Ótima pergunta! A temperatura atual na cidade de " + cidade + " é: "
                    + temperatura
                    + "°";

        } catch (Exception e) {

            return "Desculpe, não consigo te informar isso agora :(";

        }
    }

    /**
     * @return
     */
    public static String sendHours() {
        return "Hoje é " + getDiaSemana() + ", e são: " + getHora();
    }

    /**
     * @param update
     * @return
     */
    static SendMessage responseBot(Update update) {
        String textoMensagem = update.getMessage().getText().toLowerCase();
        String chatId = update.getMessage().getChatId().toString();
        String resposta = "";

        Matcher ola = Pattern.compile("\\b(?:ol(a|à)|oi|start)\\b").matcher(textoMensagem);
        Matcher temperatura = Pattern.compile("\\b(?:tempo|clima|temperatura)\\b").matcher(textoMensagem);
        Matcher horas = Pattern.compile("\\b(?:horas|hora|hor(a|à)rio)\\b").matcher(textoMensagem);

        if (ola.find()) {
            resposta = "\u2601 Olá, eu sou a WENDY. Estou aqui para lhe auxiliar a entender o sentido da vida! "
                    + "Pode me perguntar sobre o clima, o dia, filmes em cartaz no cinema e até um pouco sobre mim se quiser! :3";

        } else if (temperatura.find()) {
            resposta = sendWeather();
        } else if (horas.find()) {
            resposta = sendHours();
        } else {
            resposta = "Não entendi!\nPoderia repetir a pergunta novamente?";
        }

        return SendMessage.builder()
                .text(resposta)
                .chatId(chatId)
                .build();
    }

}
