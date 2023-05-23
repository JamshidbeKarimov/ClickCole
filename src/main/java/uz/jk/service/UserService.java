package uz.jk.service;

import uz.jk.model.User;
import uz.jk.repository.user.UserRepository;
import uz.jk.repository.user.UserRepositoryImpl;

import java.util.UUID;

public class UserService {
    private final UserRepository userRepository = new UserRepositoryImpl();

    public User getUserById(UUID id) {
        return userRepository.getById(id).orElseThrow(() ->
                new RuntimeException("user not found")
        );
    }

    public User findByChatId(Long chatId) {
        return userRepository.findByChatId(chatId).orElseThrow(() ->
                new RuntimeException("user not found")
        );
    }
}
