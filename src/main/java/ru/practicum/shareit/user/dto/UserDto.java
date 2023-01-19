package ru.practicum.shareit.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class UserDto {
    /**
     * id пользователя в системе, уникальное
     */
    private int id;

    /** имя пользователя */
    private String name;

    /** email пользователя */
    @Email(message = "Электронная почта указана неверно")
    private String email;
}