package fiap.java;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.Locale;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class UtilsBot {

    public static String getData() {
        LocalDate dataHoje = LocalDate.now();
        return dataHoje.getDayOfMonth() + " de " +
               dataHoje.getMonth().getDisplayName(TextStyle.FULL, new Locale("pt")) +
               " de " +
               dataHoje.getYear() + ".";
    }

    public static String getHora() {
        return new SimpleDateFormat("HH:mm:ss").format(new Date());
    }

    public static String getDiaSemana() {
        
        return LocalDate.now().getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("pt"));
    }

    public static String sendWeek() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dt_atual = formatter.format(new Date());
        return "Hoje é " + LocalDate.now().getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("pt")) + ", " + dt_atual;
    }

    static SendMessage responseBot(Update update) {
        String textoMensagem = update.getMessage().getText().toLowerCase();
        String chatId = update.getMessage().getChatId().toString();
        String resposta = "";

        if (textoMensagem.startsWith("ola") ||
                textoMensagem.startsWith("olá") ||
                textoMensagem.startsWith("oi") ||
                textoMensagem.startsWith("/start")) {
            resposta = "\u2601 Olá, eu sou a WENDY. Estou aqui para lhe auxiliar a entender o sentido da vida!\n"
                    +
                    "Utilize um dos comandos:\nolá\ndata\nhora\nquem é você?\nque dia é hoje?";

        } else if ("data".equals(textoMensagem)) {
            resposta = "Hoje é " + getDiaSemana() + ", " + getData();
        } else if (textoMensagem.startsWith("hora")) {
            resposta = getHora();
        } else if (textoMensagem.startsWith("quem é voc") || textoMensagem.startsWith("quem e voce")) {
            resposta = "\u2601 Eu sou a WENDY e sou um bot.";
        } else if (textoMensagem.startsWith("que dia é hoje")) {
            resposta = sendWeek();     
        } else if (textoMensagem.startsWith("/help")) {
            resposta = "Utilize um dos comandos:\nolá\ndata\nhora\nquem é você?\nque dia é hoje?";
        } else {
            resposta = "Não entendi!\nDigite /help para ver os comandos disponíveis.";
        }

        return SendMessage.builder()
                .text(resposta)
                .chatId(chatId)
                .build();
    }


}
