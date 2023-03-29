package ru.practicum.shareit.request.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * структура данных для создания запроса на вещь
 */

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ItemRequestCreateRequest {
    String description;
}
