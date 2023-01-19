package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserStorage userStorage;

    /**
     * получить объект User по id
     *
     * @param userId id пользователя
     * @return объект User
     */
    public User getById(int userId) {
        return userStorage.getById(userId);
    }

    /**
     * получить всех пользователей
     *
     * @return список объектов типа User
     */
    public List<User> getAll() {
        return userStorage.getAll();
    }

    /**
     * создать нового пользователя в хранилище
     *
     * @param user заполненный объект User
     * @return заполненный объект User
     */
    public User create(User user) {
        return userStorage.create(user);
    }

    /**
     * изменить данные существующего пользователя в хранилище
     *
     * @param user объект User, частично или полностью заполненный данными
     * @return заполненный объект User
     */
    public User update(User user) {
        return userStorage.update(user);
    }

    /**
     * удалить пользователя из хранилища
     *
     * @param userId id пользователя
     */
    public void delete(int userId) {
        userStorage.delete(userId);
    }
}