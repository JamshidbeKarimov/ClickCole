package uz.jk.repository.card;

import uz.jk.model.Card;
import uz.jk.model.User;

import java.util.*;

public class CardRepositoryImpl implements CardRepository {
    private final String path = "C:\\my\\g23\\ClickCole\\src\\main\\resources\\cards.json";

    @Override
    public Optional<Card> save(Card card) {
        List<Card> cards = readFromFile(path, Card.class);
        cards.add(card);
        writeToFile(cards, path);
        return Optional.of(card);
    }

    @Override
    public Optional<Card> getById(UUID id) {
        List<Card> cards = readFromFile(path, Card.class);

        for (Card card : cards) {
            if(Objects.equals(card.getId(), id)) {
                return Optional.of(card);
            }
        }
        return Optional.empty();
    }

    @Override
    public int deleteById(UUID id) {
        List<Card> cards = readFromFile(path, Card.class);
        for (Card card : cards) {
            if(Objects.equals(card.getId(), id)) {
                card.setIsActive(false);
                writeToFile(cards, path);
                return 204;
            }
        }
        return 404;
    }

    @Override
    public List<Card> getAll() {
        return readFromFile(path, Card.class);
    }

    @Override
    public int update(Card update) {
        List<Card> cards = readFromFile(path, Card.class);
        for (int i = 0; i < cards.size(); i++) {
            Card card = cards.get(i);
            if(Objects.equals(card.getId(), update.getId())) {
                Card card1 = updateCard(card, update);
                writeToFile(cards, path);
                return 203;
            }
        }

        return 404;
    }

    private Card updateCard(Card card, Card update) {
        if(update.getBalance() != null) {
            card.setBalance(update.getBalance());
        }

        if(update.getNumber() != null) {
            card.setNumber(update.getNumber());
        }

        return card;
    }

    @Override
    public Optional<Card> getByNumber(String number) {
        List<Card> cards = readFromFile(path, Card.class);
        for (Card card : cards) {
            if(Objects.equals(card.getNumber(), number)) {
               return Optional.of(card);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Card> getUserCards(UUID userId) {
        List<Card> userCards = new ArrayList<>();

        for (Card card : readFromFile(path, Card.class)) {
            if(Objects.equals(card.getOwnerId(), userId)) {
                userCards.add(card);
            }
        }

        return userCards;
    }
}
