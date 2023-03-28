package ru.practicum.shareit.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
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
    private String email;
}