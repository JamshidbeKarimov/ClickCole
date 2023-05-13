package uz.jk.repository.card;

import uz.jk.model.Card;
import uz.jk.repository.BaseRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CardRepository extends BaseRepository<Card> {
    Optional<Card> getByNumber(String number);

    List<Card> getUserCards(UUID userId);

}
