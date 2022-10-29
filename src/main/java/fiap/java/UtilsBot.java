package fiap.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.Normalizer;
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

    static boolean solicitouFilmes = false;

    /**
     * @param str
     * @return
     */
    public static String removerAcentos(String str) {
        return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }

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
     * @param textoMensagem
     *                      Perguntando qual o genero desejado, caso ja tenha
     *                      informado, chama funcao sendFilmes
     * @return
     */
    public static String askGenero(String textoMensagem) {
        if (!solicitouFilmes) {
            solicitouFilmes = true;
            return "Qual genero você deseja buscar? Tente algo como, Terror, Comédia, Suspense, Animação, Ação";
        } else {
            return sendFilmes(textoMensagem);
        }
    }

    /**
     * @param genero
     *               Retorna lista de filmes de acordo com genero passado por
     *               parametro
     * @return
     */
    public static String sendFilmes(String genero) {
        Matcher suspense = Pattern.compile("\\b(?:suspense)\\b").matcher(genero);
        Matcher terror = Pattern.compile("\\b(?:terror)\\b").matcher(genero);
        Matcher comedia = Pattern.compile("\\b(?:comedia)\\b").matcher(genero);
        Matcher animacao = Pattern.compile("\\b(?:animacao|desenho|animado|animacoes)\\b").matcher(genero);
        Matcher acao = Pattern.compile("\\b(?:acao)\\b").matcher(genero);
        String resposta;

        if (suspense.find()) {
            resposta = "\u2022 Órfã 2: A Origem\n\u2022 Não! Não Olhe!\n\u2022 Não Se Preocupe, Querida\n\u2022 A Queda\n\u2022 Amsterdam\n\u2022 Caça Implacável";
        } else if (terror.find()) {
            resposta = "\u2022 Halloween Ends\n\u2022 Morte, Morte, Morte\n\u2022 Sorria\n\u2022 Mostra Punk:Êra Punk\n\u2022 Mostra Punk: American Hardcore\n\u2022 Mostra Punk: A Volta dos Mortos-Vivo\n\u2022 O Fim do Mundo, Enfim\n\u2022 Juventude Decadente";
        } else if (comedia.find()) {
            resposta = "\u2022 Rir Para Não Chorar\n\u2022 Os Suburbanos - O Filme\n\u2022 Boa Sorte, Leo Grande\n\u2022 Ingresso Para o Paraíso\n\u2022 A Felicidade das Pequenas Coisas\n\u2022 Homens à Beira de Um Ataque de Nervos\n\u2022 Peter von Kant";
        } else if (animacao.find()) {
            resposta = "\u2022 As Aventuras de Tadeo e a Tábua de Esmeralda\n\u2022 Minions 2: A Origem de Gru\n\u2022 A Liga dos Superpets\n\u2022 One Piece Film: Red";
        } else if (acao.find()) {
            resposta = "\u2022 Tudo Em Todo O Lugar Ao Mesmo Tempo\n\u2022 A Mulher Rei\n\u2022 Caça Implacável\n\u2022 Adão Negro\n\u2022 Pantera Negra: Wakanda Para Sempre";
        } else {
            resposta = "Não encontrei o genero buscado :(\nPosso te auxiliar com outros generos caso queira...";
        }
        solicitouFilmes = false;
        return resposta;
    }

    /**
     * @param update
     * @return
     */
    static SendMessage responseBot(Update update) {
        String textoMensagem = removerAcentos(update.getMessage().getText().toLowerCase());
        String chatId = update.getMessage().getChatId().toString();
        String resposta = "";

        Matcher ola = Pattern.compile("\\b(?:ol(a|à)|oi|start)\\b").matcher(textoMensagem);
        Matcher temperatura = Pattern.compile("\\b(?:tempo|clima|temperatura)\\b").matcher(textoMensagem);
        Matcher horas = Pattern.compile("\\b(?:horas|hora|hor(a|à)rio)\\b").matcher(textoMensagem);
        Matcher filme = Pattern.compile("\\b(?:filme|cartaz|cinema|filmes)\\b").matcher(textoMensagem);
        Matcher idade = Pattern.compile("\\b(?:idade|ano|anos)\\b").matcher(textoMensagem);

        if (ola.find()) {
            resposta = "\u2601 Olá, eu sou a WENDY. Estou aqui para lhe auxiliar a entender o sentido da vida! "
                    + "Pode me perguntar sobre o clima, o dia, filmes em cartaz no cinema e até um pouco sobre mim se quiser! :3";

        } else if (temperatura.find()) {
            resposta = sendWeather();
        } else if (horas.find()) {
            resposta = sendHours();
        } else if ((filme.find() || solicitouFilmes)) {
            resposta = askGenero(textoMensagem);
        } else if (idade.find()) {
            resposta = askAge();
             
        } else {
            resposta = "Não entendi!\nPoderia repetir a pergunta novamente?";
        }

        return SendMessage.builder()
                .text(resposta)
                .chatId(chatId)
                .build();
    }

    public static String askAge() {
        return "Isso não é algo que você deveria perguntar à uma dama";
    }

}