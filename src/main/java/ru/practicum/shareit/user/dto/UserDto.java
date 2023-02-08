package ru.practicum.shareit.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;

@Data
@AllArgsConstructor
public class UserDto {
    /**
     * id пользователя в системе, уникальное
     */
    private long id;

    /**
     * имя пользователя
     */
    private String name;

    /**
     * email пользователя
     */
    @Email(message = "Электронная почта указана неверно")
    private String email;
}