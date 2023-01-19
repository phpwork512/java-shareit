package ru.practicum.shareit.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * TODO Sprint add-controllers.
 */
@Data
@AllArgsConstructor
public class User {
    /** id пользователя в системе, уникальное */
    private int id;

    /** имя пользователя */
    @NotBlank(message = "Имя пользователя не может быть пустым")
    private String name;

    /** email пользователя, должен быть уникальным */
    @Email(message = "Электронная почта указана неверно")
    @NotBlank(message = "Электронная почта не может быть пустой")
    private String email;
}