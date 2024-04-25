package uz.haqnazarov.demo;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.LinkedList;
import java.util.List;

@Component
public class DemoBot extends TelegramLongPollingBot {

    @Autowired
    public DemoBot(TelegramBotsApi api) throws TelegramApiException {
        api.registerBot(this);
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        Long chatId = message.getChatId();
        if (message.hasText()) {
            String text = message.getText();
            if (text.equalsIgnoreCase("/start")) {
                execute(SendMessage.builder().chatId(chatId).text("Salom botga xush kelibsiz").replyMarkup(menuButtons()).build());
            } else if (text.equalsIgnoreCase("Men haqimda")) {
                String t = "Mening ismim: " + message.getFrom().getFirstName() +
                           "\nMening ID raqamim: " + chatId;
                execute(SendMessage.builder().chatId(chatId).text(t).build());
            }
        }
    }

    private ReplyKeyboard menuButtons() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        List<KeyboardRow> rows = new LinkedList<>();
        KeyboardRow row = new KeyboardRow();
        KeyboardButton button = new KeyboardButton();
        button.setText("Men haqimda");
        row.add(button);
        rows.add(row);
        replyKeyboardMarkup.setKeyboard(rows);
        return replyKeyboardMarkup;
    }

    @Override
    public String getBotUsername() {
        return "haqnazarovytbot";
    }

    @Override
    public String getBotToken() {
        return "6957537166:AAGUTQ2fMKTzl_bnW4XVj1UaRt5AM5mAKMg";
    }
}
