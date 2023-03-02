package ru.practicum.shareit.request.dto;

import lombok.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemSmallDto;
import ru.practicum.shareit.item.model.Item;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@Builder
public class ItemRequestDto {
    /**
     * id запроса в системе, уникальное
     */
    private long id;

    /**
     * текст запроса
     */
    @NotNull
    private String description;

    private LocalDateTime created;

    private List<ItemSmallDto> items;

}