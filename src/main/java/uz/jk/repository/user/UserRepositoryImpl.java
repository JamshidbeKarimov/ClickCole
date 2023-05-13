package uz.jk.repository.user;

import uz.jk.model.User;
import uz.jk.model.UserState;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class UserRepositoryImpl implements UserRepository {

    String path = "C:\\my\\g23\\ClickCole\\src\\main\\resources\\users.json";

    @Override
    public Optional<User> save(User user) {
        List<User> users = readFromFile(path, User.class);
        users.add(user);
        writeToFile(users, path);
        return Optional.of(user);
    }

    @Override
    public Optional<User> getById(UUID id) {
        List<User> users = readFromFile(path, User.class);
        for (User user : users) {
            if (Objects.equals(user.getId(), id)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    @Override
    public int deleteById(UUID id) {
        List<User> users = readFromFile(path, User.class);
        for (User user : users) {
            if (Objects.equals(user.getId(), id)) {
                users.remove(user);
                writeToFile(users, path);
                return 204;
            }
        }
        return 404;
    }

    @Override
    public List<User> getAll() {
        return readFromFile(path, User.class);
    }

    @Override
    public int update(User update) {
        List<User> users = readFromFile(path, User.class);
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            if(Objects.equals(user.getId(), update.getId())) {
                User user1 = updateUser(user, update);
              writeToFile(users, path);
              return 203;
            }
        }

        return 404;
    }

    private User updateUser(User user, User update) {
        if(update.getFirstname() != null) {
            user.setFirstname(update.getFirstname());
        }

        if(update.getLocation() != null) {
            user.setLocation(update.getLocation());
        }

        if(update.getPhoneNumber() != null) {
            user.setPhoneNumber(update.getPhoneNumber());
        }

        if(update.getState() != null) {
            user.setState(update.getState());
        }

        return user;

    }

    @Override
    public Optional<User> findByChatId(Long chatId) {
        List<User> users = readFromFile(path, User.class);
        for (User user : users) {
            if (Objects.equals(user.getChatId(), chatId)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    @Override
    public void updateState(Long chatId, UserState newState) {
        List<User> users = readFromFile(path, User.class);
        for (User user : users) {
            if (Objects.equals(user.getChatId(), chatId)) {
               user.setState(newState);
               writeToFile(users, path);
               return;
            }
        }

    }
}
