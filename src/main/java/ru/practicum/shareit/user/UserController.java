package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exceptions.UserEmailNotUniqueException;
import ru.practicum.shareit.user.dto.UserDto;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO Sprint add-controllers.
 */

@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    /**
     * создать пользователя, присвоить id, ручка POST /users
     *
     * @param user провалидированные данные пользователя
     * @return заполненный объект UserDto
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@Valid @RequestBody User user) {
        log.info("Create user: " + user.toString());

        //дополнительная валидация объекта User
        UserValidator.validate(user, userService);

        return UserDtoMapper.toUserDto(userService.create(user));
    }

    /**
     * изменить данные пользователя, ручка PATCH /users/{userId}
     *
     * @param userId  id пользователя
     * @param userDto объект UserDto с данными, которые нужно изменить
     * @return заполненный объект UserDto
     */
    @PatchMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto update(@PathVariable int userId, @Valid @RequestBody UserDto userDto) {
        userDto.setId(userId);
        log.info("Update user {}: " + userDto, userId);

        User user = UserDtoMapper.toUser(userDto);

        //дополнительная валидация объекта User
        UserValidator.validate(user, userService);

        return UserDtoMapper.toUserDto(userService.update(user));
    }

    /**
     * запрос данных всех пользователей (GET /users)
     *
     * @return список объектов User
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> getAll() {
        log.info("Get all users");
        return UserDtoMapper.toUserDtoList(userService.getAll());
    }

    /**
     * запрос данных пользователя  (GET /users/{userId})
     *
     * @param userId id пользователя
     * @return список объектов User
     */
    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto get(@PathVariable int userId) {
        log.info("Get userId {}", userId);
        return UserDtoMapper.toUserDto(userService.getById(userId));
    }

    /**
     * удалить пользователя (DELETE /users/{userId})
     *
     * @param userId id пользователя
     */
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable int userId) {
        log.info("Delete userId {}", userId);
        userService.delete(userId);
    }

    /**
     * утилитарный класс для дополнительной валидации модели User
     */
    private static class UserValidator {
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

    /**
     * утилитарный класс для преобразования User <--> UserDto
     */
    private static class UserDtoMapper {

        /**
         * преобразовать dto в бизнес-объект User
         *
         * @param userDto объект UserDto
         * @return объект User или null если на входе был null
         */
        public static User toUser(UserDto userDto) {
            if (userDto != null) return new User(userDto.getId(), userDto.getName(), userDto.getEmail());
            else return null;
        }

        /**
         * преобразовать бизнес-объект User в dto
         *
         * @param user объект User
         * @return объект UserDto или null если на входе был null
         */
        public static UserDto toUserDto(User user) {
            if (user != null) return new UserDto(user.getId(), user.getName(), user.getEmail());
            else return null;
        }

        /**
         * преобразовать список бизнес-объектов User в список dto
         *
         * @param userList список объектов User
         * @return список объектов UserDto
         */
        public static List<UserDto> toUserDtoList(List<User> userList) {
            List<UserDto> userDtoList = new ArrayList<>();

            if (userList != null) {
                for (User user : userList) {
                    userDtoList.add(toUserDto(user));
                }
            }

            return userDtoList;
        }
    }
}