package ru.practicum.shareit.user;

import ru.practicum.shareit.exceptions.UserEmailNotUniqueException;

import java.util.List;

public class UserValidator {
    /**
     * дополнительная валидация пользователя: проверка уникальности e-mail
     *
     * @param user        объект User
     * @param userService объект-сервис работы с пользователями
     * @throws UserEmailNotUniqueException если обнаружено, что e-mail не уникальный
     */
    public static void validate(User user, UserService userService) throws UserEmailNotUniqueException {
        //дополнительная проверка уникальности e-mail среди всех записей в хранилище пользователей
        boolean isNewUser = user.getId() == 0;
        String userEmail = user.getEmail();

        //e-mail может быть пустым при обновлении существующего пользователя
        if (isNewUser || userEmail != null && !userEmail.isBlank()) {
            List<User> allUsers = userService.getAll();
            for (User userEntry : allUsers) {
                if (userEntry.getEmail().equals(userEmail)) {
                    if (isNewUser || userEntry.getId() != user.getId()) {
                        throw new UserEmailNotUniqueException("E-mail не уникален");
                    }
                }
            }
        }
    }
}