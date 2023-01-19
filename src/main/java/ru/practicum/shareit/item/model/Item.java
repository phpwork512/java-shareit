package ru.practicum.shareit.item.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * TODO Sprint add-controllers.
 */
@Data
@AllArgsConstructor
public class Item {

    /**
     * id вещи в системе, уникальное
     */
    private int id;

    /**
     * id пользователя-владельца
     */
    private int ownerId;

    /**
     * название вещи
     */
    @NotBlank(message = "Название вещи не может быть пустым")
    private String name;

    /**
     * описание вещи
     */
    @NotBlank(message = "Описание вещи не может быть пустым")
    private String description;

    /**
     * статус вещи
     * true - можно брать в аренду
     * false - нельзя брать в аренду
     */
    @NotNull
    private Boolean available;
}