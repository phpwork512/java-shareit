package ru.practicum.shareit.user;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.exceptions.UserNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryUserStorage implements UserStorage {

    /**
     * счетчик для присваивания уникальных id
     */
    int nextId = 0;

    /**
     * хранилище объектов User
     */
    private final Map<Integer, User> storageMap = new HashMap<>();

    /**
     * получить объект User по id
     *
     * @param userId id пользователя
     * @return объект User
     * @throws UserNotFoundException если пользователь с таким id не найден
     */
    @Override
    public User getById(int userId) throws UserNotFoundException {
        if (storageMap.containsKey(userId)) {
            return storageMap.get(userId);
        } else {
            throw new UserNotFoundException("Пользователь не найден");
        }
    }

    /**
     * получить всех пользователей
     * @return список объектов типа User
     */
    @Override
    public List<User> getAll() {
        return new ArrayList<>(storageMap.values());
    }

    /**
     * создать нового пользователя в хранилище
     * @param user заполненный объект User
     * @return заполненный объект User
     */
    @Override
    public User create(User user) {
        if (user != null) {
            user.setId(++nextId);
            storageMap.put(user.getId(), user);
        }

        return user;
    }

    /**
     * изменить данные существующего пользователя в хранилище
     * @param user объект User, частично или полностью заполненный данными; можно менять поля email и name, поиск ведётся по id
     * @return заполненный объект User
     */
    @Override
    public User update(User user) throws UserNotFoundException {
        if (user != null) {
            User storageUser = storageMap.get(user.getId());
            if (storageUser != null) {
                if (user.getEmail() != null && !user.getEmail().isBlank()) storageUser.setEmail(user.getEmail());
                if (user.getName() != null && !user.getName().isBlank()) storageUser.setName(user.getName());
            } else {
                throw new UserNotFoundException("Пользователь не найден");
            }

            return storageUser;
        }

        return user;
    }

    /**
     * удалить пользователя из хранилища
     * @param userId id пользователя
     */
    @Override
    public void delete(int userId) {
        storageMap.remove(userId);
    }
}