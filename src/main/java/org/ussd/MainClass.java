package org.ussd;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class MainClass {
    public static void main(String[] args) {
        try {
            TelegramBotsApi tg = new TelegramBotsApi(DefaultBotSession.class);
            tg.registerBot(new BotClass());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
