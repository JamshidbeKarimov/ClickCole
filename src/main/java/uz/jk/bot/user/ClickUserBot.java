package uz.jk.bot.user;

import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import uz.jk.model.User;
import uz.jk.model.UserState;
import uz.jk.repository.user.UserRepository;
import uz.jk.repository.user.UserRepositoryImpl;

import java.util.Optional;

public class ClickUserBot extends TelegramLongPollingBot {
    private final UserRepository userRepository = new UserRepositoryImpl();
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
                }
            } else {
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
                    execute(userBotService.userCards(chatId.toString(), currentUserOptional.get().getId()));
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
