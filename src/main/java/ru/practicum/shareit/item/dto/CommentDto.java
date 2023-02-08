package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class CommentDto {
    private long id;

    @NotNull
    private String text;

    private String authorName;
    private LocalDateTime created;
}