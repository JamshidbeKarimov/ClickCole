package uz.jk.bot.user;

import org.telegram.telegrambots.meta.api.methods.groupadministration.SetChatPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import uz.jk.model.Card;
import uz.jk.model.UserState;
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

    public SendMessage userCards(String chatId) {
//        List<Card> cards = cardRepository.getUserCards(userId);

//        StringBuilder message = new StringBuilder();
//        if (cards.isEmpty()) {
//            message.append("You don't have any card");
//        }
//        else {
//            int i = 1;
//            for (Card card : cards) {
//                message.append(i++).append(". ").append(card.getNumber()).append(" ")
//                        .append(card.getBalance()).append(" ").append(card.getType()).append("\n");
//            }
//        }

        SendMessage sendMessage = new SendMessage(chatId, "Card menu");
        sendMessage.setReplyMarkup(buttons.cardMenu());
        return sendMessage;
    }

    public SendMessage askCardType(String chatId) {
        SendMessage sendMessage = new SendMessage(chatId, "Choose cardType");
        sendMessage.setReplyMarkup(buttons.cardTypes());
        return sendMessage;
    }


    public DeleteMessage deleteMessage(String chatId, Integer messageId){
        return new DeleteMessage(chatId, messageId);
    }

    public SendMessage askCardNumber(String chatId) {
        return new SendMessage(chatId, "Enter card number");
    }

    public UserState extractCardType(String type) {
        type = type.substring(4);
        return UserState.valueOf(type);
    }

    public boolean validateCardNumber(String number) {
        return number.matches("\\d{4}");
    }


    public SendMessage enterValidCardNumber(String chatId) {
        return new SendMessage(chatId, "Enter only numbers, 16( 4 bizga yetadi)");
    }
}
