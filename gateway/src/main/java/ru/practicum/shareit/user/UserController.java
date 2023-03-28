package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Slf4j
@Validated
public class UserController {

    private final UserClient userClient;

    /**
     * создать пользователя, присвоить id, ручка POST /users
     *
     * @param user провалидированные данные пользователя
     * @return заполненный объект UserDto
     */
    @PostMapping
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
        log.info("Create user {}", user);
        return userClient.createUser(user);
    }

    /**
     * изменить данные пользователя, ручка PATCH /users/{userId}
     *
     * @param userId  id пользователя
     * @param userDto объект UserDto с данными, которые нужно изменить
     * @return заполненный объект UserDto
     */
    @PatchMapping("/{userId}")
    public ResponseEntity<Object> updateUser(@Positive @PathVariable long userId, @Valid @RequestBody UserDto userDto) {
        log.info("Update userId {}, userDto {}", userId, userDto);
        return userClient.updateUser(userId, userDto);
    }

    /**
     * запрос данных всех пользователей (GET /users)
     *
     * @return список объектов User
     */
    @GetMapping
    public ResponseEntity<Object> getAllUsers() {
        log.info("Get all users");
        return userClient.getAllUsers();
    }

    /**
     * запрос данных пользователя  (GET /users/{userId})
     *
     * @param userId id пользователя
     * @return список объектов User
     */
    @GetMapping("/{userId}")
    public ResponseEntity<Object> getUser(@Positive @PathVariable Long userId) {
        log.info("Get userId {}", userId);
        return userClient.getUser(userId);
    }

    /**
     * удалить пользователя (DELETE /users/{userId})
     *
     * @param userId id пользователя
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> deleteUser(@Positive @PathVariable int userId) {
        log.info("Delete userId {}", userId);
        return userClient.deleteUser(userId);
    }
}