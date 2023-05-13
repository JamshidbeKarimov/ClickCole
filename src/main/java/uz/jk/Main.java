package uz.jk;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import uz.jk.bot.user.ClickUserBot;
import uz.jk.model.Card;
import uz.jk.model.CardType;
import uz.jk.model.User;
import uz.jk.repository.card.CardRepository;
import uz.jk.repository.card.CardRepositoryImpl;
import uz.jk.repository.user.UserRepository;
import uz.jk.repository.user.UserRepositoryImpl;

import java.time.LocalDate;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
//        UserRepository userRepository = new UserRepositoryImpl();
//        User user = User.builder()
//                .firstname("helllo")
//                .phoneNumber("test")
//                .build();
//
//        user.setId(UUID.fromString("b9578ad4-9436-43f7-ab7c-7de496892642"));
//        user.setFirstname("this was hello");
////        userRepository.save(user);
//        userRepository.update(user);

        CardRepository cardRepository = new CardRepositoryImpl();
        cardRepository.save(
                Card.builder()
                        .balance(100D)
                        .expiry(LocalDate.of(2027, 03, 04))
                        .number("1234")
                        .type(CardType.HUMO)
                        .ownerId(UUID.fromString("5906e66d-66d8-4309-9663-957680ee1541"))
                        .build());


        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new ClickUserBot());
            System.out.println("go on");
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

    }
}