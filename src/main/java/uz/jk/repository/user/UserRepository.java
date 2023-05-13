package uz.jk.repository.user;

import uz.jk.model.User;
import uz.jk.model.UserState;
import uz.jk.repository.BaseRepository;

import java.util.Optional;

public interface UserRepository extends BaseRepository<User> {

    Optional<User> findByChatId(Long chatId);

    void updateState(Long chatId, UserState newState);
}
