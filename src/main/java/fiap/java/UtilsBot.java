package fiap.java;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class UtilsBot {

    public static String getData() {
        return new SimpleDateFormat("dd/MM/yyyy").format(new Date());
    }

    public static String getHora() {
        return new SimpleDateFormat("HH:mm:ss").format(new Date());
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
                    "Utilize um dos comandos:\nolá\ndata\nhora\nquem é você?";

        } else if ("data".equals(textoMensagem)) {
            resposta = getData();
        } else if (textoMensagem.startsWith("hora")) {
            resposta = getHora();
        } else if (textoMensagem.startsWith("quem é voc") || textoMensagem.startsWith("quem e voce")) {
            resposta = "\u2601 Eu sou a WENDY e sou um bot.";
        } else if (textoMensagem.startsWith("/help")) {
            resposta = "Utilize um dos comandos:\nolá\ndata\nhora\nquem é você?";
        } else {
            resposta = "Não entendi!\nDigite /help para ver os comandos disponíveis.";
        }

        return SendMessage.builder()
                .text(resposta)
                .chatId(chatId)
                .build();
    }

}
