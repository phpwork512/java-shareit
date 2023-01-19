package ru.practicum.shareit.request;

import ru.practicum.shareit.user.User;

import javax.validation.constraints.NotBlank;

/**
 * TODO Sprint add-item-requests.
 */
public class ItemRequest {
    /**
     * id запроса в системе, уникальное
     */
    private int requestId;

    /**
     * пользователь, который создал запрос
     */
    private User user;

    /**
     * текст запроса
     */
    @NotBlank(message = "Текст запроса не может быть пустым")
    String requestText;
}