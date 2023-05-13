package uz.jk.bot.user;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import uz.jk.model.Card;
import uz.jk.repository.card.CardRepository;
import uz.jk.repository.card.CardRepositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserBotService {
    private final Buttons buttons = new Buttons();
    private final CardRepository cardRepository = new CardRepositoryImpl();

    public SendMessage askForContact(String chatId) {
        SendMessage sendMessage = new SendMessage(chatId, "Please share your number");
        sendMessage.setReplyMarkup(buttons.shareContact());
        return sendMessage;
    }

    public SendMessage askForLocation(String chatId) {
        SendMessage sendMessage = new SendMessage(chatId, "Please share your location");
        sendMessage.setReplyMarkup(buttons.shareLocation());
        return sendMessage;
    }

    public SendMessage showMainMenu(String chatId) {
        SendMessage sendMessage = new SendMessage(chatId, "Main menu");
        sendMessage.setReplyMarkup(buttons.mainMenu());
        return sendMessage;
    }

    public SendMessage userCards(String chatId, UUID userId) {
        List<Card> cards = cardRepository.getUserCards(userId);

        StringBuilder message = new StringBuilder();
        if (cards.isEmpty()) {
            message.append("You don't have any card");
        }
        else {
            int i = 1;
            for (Card card : cards) {
                message.append(i++).append(". ").append(card.getNumber()).append(" ")
                        .append(card.getBalance()).append(" ").append(card.getType()).append("\n");
            }
        }

        SendMessage sendMessage = new SendMessage(chatId, message.toString());
        sendMessage.setReplyMarkup(buttons.cards(cards));
        return sendMessage;
    }




}
