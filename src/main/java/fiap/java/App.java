package fiap.java;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class App 
{
    public static void main( String[] args )
    {

        System.out.println( UtilsBot.getDiaSemana() + ", " + UtilsBot.getData());

        try {
            // Instanciar API do bot do Telegram;
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            // registrar nosso bot WENDY;
            botsApi.registerBot(new CommandsBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
