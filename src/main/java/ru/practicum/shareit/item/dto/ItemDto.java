package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * TODO Sprint add-controllers.
 */
@Data
@AllArgsConstructor
public class ItemDto {
    /**
     * id вещи в системе, уникальное
     */
    private int id;

    /**
     * название вещи
     */
    private String name;

    /**
     * описание вещи
     */
    private String description;

    /**
     * статус вещи
     * true - можно брать в аренду
     * false - нельзя брать в аренду
     */
    private Boolean available;
}
