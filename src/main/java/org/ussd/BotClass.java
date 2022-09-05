package org.ussd;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class BotClass extends TelegramLongPollingBot {

    @Override
    public String getBotUsername() {
        return "ussd_robot";
    }

    @Override
    public String getBotToken() {
        return "1343013669:AAEpfzmRYjuP8RAzvZ8IfNtC96W4pxSDOew";
    }

    private void send_message(String text, Update update){
        SendMessage msg = new SendMessage();
        msg.setText(text);
        msg.setChatId(update.getMessage().getChatId().toString());
        try{
            execute(msg);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        long chat_id = update.getMessage().getChatId();
        User from = update.getMessage().getFrom();
        String text = update.getMessage().getText();
        if(update.hasMessage() && update.getMessage().hasText()){
            if(text.equals("/start")) send_message("Salom " + from.getFirstName(), update);
            if(text.equalsIgnoreCase("whoami")) send_message(from.getFirstName() +
                    " " + from.getLastName()==null?"":from.getLastName(), update);
        }
    }


}
