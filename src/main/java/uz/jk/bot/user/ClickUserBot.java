package uz.jk.bot.user;

import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.*;
import uz.jk.model.Card;
import uz.jk.model.CardType;
import uz.jk.model.User;
import uz.jk.model.UserState;
import uz.jk.repository.card.CardRepository;
import uz.jk.repository.card.CardRepositoryImpl;
import uz.jk.repository.user.UserRepository;
import uz.jk.repository.user.UserRepositoryImpl;

import java.util.Optional;

public class ClickUserBot extends TelegramLongPollingBot {
    private final UserRepository userRepository = new UserRepositoryImpl();
    private final CardRepository cardRepository = new CardRepositoryImpl();

    private final UserBotService userBotService = new UserBotService();
    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage()) {
            Message message = update.getMessage();
            String text = message.getText();
            Long chatId = message.getChatId();

            Optional<User> currentUserOptional = userRepository.findByChatId(chatId);

            UserState userState;

            if(currentUserOptional.isPresent()) {

                User currentUser = currentUserOptional.get();
                userState = currentUser.getState();
                switch (userState) {
                    case SHARED_CONTACT -> {
                        if(message.hasLocation()) {
                            Location location = message.getLocation();
                            currentUser.setLocation(
                                    new uz.jk.model.Location(location.getLatitude(), location.getLatitude())
                            );
                            currentUser.setState(UserState.REGISTERED);
                            userRepository.update(currentUser);
                            userState = UserState.REGISTERED;
                        }
                    }
                    case REGISTERED -> {
                        if(text.equals("\uD83D\uDCB3CARD")) {
                            userState = UserState.CARD_MENU;
                            userRepository.updateState(chatId, UserState.CARD_MENU);
                        }
                    }
                    case CARD_MENU -> {
                        if(text.equals("➕ Add card")) {
                            userState = UserState.ADD_CARD;
                            userRepository.updateState(chatId, UserState.ADD_CARD);
                        } else if(text.equals("⬅️Back")) {
                            userState = UserState.REGISTERED;
                            userRepository.updateState(chatId, UserState.REGISTERED);
                        }
                    }
                    case HUMO, UZ_CARD, VISA -> {
                        if(userBotService.validateCardNumber(text)) {
                            Card card = Card.builder()
                                    .number(text)
                                    .type(CardType.valueOf(userState.name()))
                                    .balance(1000D)
                                    .ownerId(currentUser.getId())
                                    .build();
                            cardRepository.save(card);
                            userRepository.updateState(chatId, UserState.CARD_MENU);
                            userState = UserState.CARD_MENU;
                        }
                    }
                }
            } 
            else {
                if(message.hasContact()) {
                    Contact contact = message.getContact();
                    User user = User.builder()
                            .phoneNumber(contact.getPhoneNumber())
                            .firstname(contact.getFirstName())
                            .chatId(chatId)
                            .state(UserState.SHARED_CONTACT)
                            .build();
                    userRepository.save(user);
                    userState = UserState.SHARED_CONTACT;
                }
                else {
                    userState = UserState.START;
                }
            }

            switch (userState) {
                case START -> {
                    execute(userBotService.askForContact(chatId.toString()));
                }
                case SHARED_CONTACT -> {
                    execute(userBotService.askForLocation(chatId.toString()));
                }
                case REGISTERED -> {
                    execute(userBotService.showMainMenu(chatId.toString()));
                }
                case CARD_MENU -> {
                    execute(userBotService.userCards(chatId.toString()));
                }
                case ADD_CARD -> {
                    execute(userBotService.askCardType(chatId.toString()));
                }
                case HUMO, UZ_CARD, VISA -> {
                    execute(userBotService.enterValidCardNumber(chatId.toString()));
                }
            }

        }
        else if(update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            String data = callbackQuery.getData();
            Message message = callbackQuery.getMessage();
            Long chatId = message.getChatId();
            User currentUser = userRepository.findByChatId(chatId).get();

            UserState userState = currentUser.getState();

            switch (userState) {
                case ADD_CARD -> {
                    execute(userBotService.deleteMessage(chatId.toString(), message.getMessageId()));
                    execute(userBotService.askCardNumber(chatId.toString()));
                    userRepository.updateState(chatId, userBotService.extractCardType(data));
                }
            }
        }

    }

    @Override
    public String getBotUsername() {
        return "G23_Payment_bot";
    }

    @Override
    public String getBotToken() {
        return "6111531134:AAGhpR5PgH8IB3STz6kbUIryCUgDMb1fCcg";
    }
}
