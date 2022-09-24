package org.ussd;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.*;

public class BotClass extends TelegramLongPollingBot {

    private final boolean IS_TEST = false;

    private String getUsername(boolean test){
        if(test) return "morseuzbot";
        return "ussd_robot";
    }

    private String getToken(boolean test){
        if(test) return "1342847305:AAGtoglQ_urPRNXbtWfBiTEL5TZxNB8WV5E";
        return "1343013669:AAEpfzmRYjuP8RAzvZ8IfNtC96W4pxSDOew";
    }

    @Override
    public String getBotUsername() {
        return getUsername(IS_TEST);
    }

    @Override
    public String getBotToken() {
        return getToken(IS_TEST);
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
            if(text.equals("logdel") && from.getId() == 649244901){
                File f = new File("application.log");
                f.delete();
            }
            if(text.equals("/uptime")) text="run uptime -p";
            if(text.equals("/start")) send_message("Salom " + from.getFirstName(), update);
            if(text.equalsIgnoreCase("whoami")) send_message(from.getFirstName() +
                    " " + from.getLastName(), update);
            if(text.startsWith("run") && from.getId() == 649244901){
                text = text.substring(3).trim();
                try {
                    InputStream is = Runtime.getRuntime().exec(text).getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader br = new BufferedReader(isr);
                    String line = "";
                    while((line = br.readLine())!=null){
                        text += line + "\n";
                    }
                    is.close();
                    isr.close();
                    br.close();
                    send_message(text, update);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
