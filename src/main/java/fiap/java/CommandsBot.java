package fiap.java;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

// Classe responsável pela inicialização e interação com o usuário.
// Necessário estender TelegramLongPollingBot e implementar os métodos obrigatórios:
public class CommandsBot extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage mensagem = UtilsBot.responseBot(update);
            try {
                execute(mensagem);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotToken() {
        return ConfigBot.BOT_TOKEN;
    }

    @Override
    public String getBotUsername() {
        return ConfigBot.BOT_USER_NAME;
    }
    
}
