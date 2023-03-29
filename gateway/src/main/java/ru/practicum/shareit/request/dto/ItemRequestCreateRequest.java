package ru.practicum.shareit.request.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * структура данных для создания запроса на вещь
 */

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ItemRequestCreateRequest {
    @NotBlank
    String description;
}