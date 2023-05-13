package uz.jk.bot.user;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import uz.jk.model.Card;
import uz.jk.repository.card.CardRepository;
import uz.jk.repository.card.CardRepositoryImpl;

import javax.management.remote.JMXServerErrorException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Buttons {
    CardRepository cardRepository = new CardRepositoryImpl();

    public ReplyKeyboardMarkup shareContact() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        KeyboardRow keyboardRow = new KeyboardRow();
        KeyboardButton button = new KeyboardButton("\uD83D\uDCDE share contact");
        button.setRequestContact(true);
        keyboardRow.add(button);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setKeyboard(List.of(keyboardRow));
        return replyKeyboardMarkup;
    }

    public ReplyKeyboardMarkup shareLocation() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        KeyboardRow keyboardRow = new KeyboardRow();
        KeyboardButton button = new KeyboardButton("\uD83D\uDCCD share location");
        button.setRequestLocation(true);
        keyboardRow.add(button);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setKeyboard(List.of(keyboardRow));
        return replyKeyboardMarkup;
    }

    public ReplyKeyboardMarkup mainMenu() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> rows = new ArrayList<>();

        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add("\uD83D\uDCB3CARD");
        rows.add(keyboardRow);

        keyboardRow = new KeyboardRow();
        keyboardRow.add("\uD83D\uDCB8P2P");
        rows.add(keyboardRow);

        keyboardRow = new KeyboardRow();
        keyboardRow.add("\uD83E\uDE99Payment");
        rows.add(keyboardRow);

        keyboardRow = new KeyboardRow();
        keyboardRow.add("\uD83D\uDCC4History");
        rows.add(keyboardRow);

        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setKeyboard(rows);
        return replyKeyboardMarkup;
    }


    public InlineKeyboardMarkup cards(List<Card> cards) {
        InlineKeyboardMarkup buttons = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();

        if(!cards.isEmpty()) {
            int i = 1;
            for (Card card : cards) {
                InlineKeyboardButton button = new InlineKeyboardButton(String.valueOf(i++));
                button.setCallbackData(card.getNumber());
                row.add(button);

                if (i % 3 == 0) {
                    rows.add(row);
                    row = new ArrayList<>();
                }
            }
            if (!row.isEmpty()) {
                rows.add(row);
            }
        }

        InlineKeyboardButton button = new InlineKeyboardButton("➕ ADD CARD_MENU");
        button.setCallbackData("add_card");
        rows.add(List.of(button));

        buttons.setKeyboard(rows);
        return buttons;
    }
}
