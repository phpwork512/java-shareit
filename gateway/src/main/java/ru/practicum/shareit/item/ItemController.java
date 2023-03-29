package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.common.Constants;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemCreateRequest;
import ru.practicum.shareit.item.dto.ItemDto;

import javax.validation.Valid;
import javax.validation.ValidationException;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ItemController {
    private final ItemClient itemClient;

    /**
     * сохранить новую вещь в хранилище, присвоить уникальный id
     *
     * @param itemCreateRequest заполненный валидированный объект ItemCreateRequest
     * @param ownerId           id пользователя, который будет указан владельцем вещи
     * @return заполненный объект ItemDto
     * @throws ValidationException если указан ошибочный id пользователя-владельца (<=0)
     */
    @PostMapping
    public ResponseEntity<Object> createItem(@Valid @RequestBody ItemCreateRequest itemCreateRequest,
                                             @Positive @RequestHeader(value = Constants.X_HEADER_NAME) int ownerId) {
        log.info("Create item {}, owner {}", ownerId, itemCreateRequest.toString());
        return itemClient.createItem(ownerId, itemCreateRequest);
    }

    /**
     * изменить данные вещи с указанным id
     *
     * @param itemId  id Вещи
     * @param itemDto заполненный объект ItemDto
     * @param ownerId id пользователя - владельца вещи
     * @return заполненный объект ItemDto
     */
    @PatchMapping("/{itemId}")
    public ResponseEntity<Object> updateItem(@Positive @PathVariable int itemId,
                                             @Valid @RequestBody ItemDto itemDto,
                                             @Positive @RequestHeader(value = Constants.X_HEADER_NAME) int ownerId) {
        log.info("Update item {}, ownerId {}: ", itemDto, ownerId);
        return itemClient.updateItem(ownerId, itemId, itemDto);
    }

    /**
     * получить список вещей, принадлежащих пользователю с указанным id
     *
     * @param ownerId id пользователя
     * @return список объектов типа ItemDto
     */
    @GetMapping
    public ResponseEntity<Object> getOwnedItemsList(@PositiveOrZero @RequestParam(defaultValue = "0") int from,
                                                    @Positive @RequestParam(defaultValue = "20") int size,
                                                    @Positive @RequestHeader(value = Constants.X_HEADER_NAME) long ownerId) {
        log.info("Get owned items list, ownerId {}, from {}, size {}", ownerId, from, size);
        return itemClient.getItems(ownerId, from, size);
    }

    /**
     * получить данные вещи по id
     *
     * @param itemId id вещи
     * @return объект типа ItemDto
     */
    @GetMapping("/{itemId}")
    public ResponseEntity<Object> getSingleItem(@Positive @PathVariable int itemId,
                                                @Positive @RequestHeader(value = Constants.X_HEADER_NAME) int ownerId) {
        log.info("Get itemId {} by userId {}", itemId, ownerId);
        return itemClient.getSingleItem(ownerId, itemId);
    }

    /**
     * поиск строки в названии или описании вещей
     *
     * @param text строка поиска
     * @return список объектов Item, которые содержат искомую строку в названиях или описаниях
     */
    @GetMapping("/search")
    public ResponseEntity<Object> search(@PositiveOrZero @RequestParam(defaultValue = "0") int from,
                                         @Positive @RequestParam(defaultValue = "20") int size,
                                         @RequestParam(defaultValue = "") String text) {
        log.info("Search text '{}', from {}, size {}", text, from, size);
        return itemClient.search(text, from, size);
    }

    /**
     * удалить вещь с указанным id из хранилища
     *
     * @param itemId  id вещи
     * @param ownerId id пользователя - владельца вещи
     */
    @DeleteMapping("/{itemId}")
    public ResponseEntity<Object> deleteItem(@Positive @PathVariable int itemId,
                                             @Positive @RequestHeader(value = Constants.X_HEADER_NAME) int ownerId) {
        log.info("Delete itemId {}, ownerId {}", itemId, ownerId);
        return itemClient.deleteItem(itemId, ownerId);
    }

    /**
     * оставить отзыв к вещи если ранее её бронировал
     *
     * @param commentDto данные отзыва
     * @param itemId     id вещи
     * @param authorId   id автора
     * @return объект CommentDto
     * @throws ValidationException если передан ошибочный параметр authorId
     */
    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> createComment(@Valid @RequestBody CommentDto commentDto,
                                                @Positive @PathVariable int itemId,
                                                @Positive @RequestHeader(value = Constants.X_HEADER_NAME) int authorId) {
        log.info("Create comment {}, itemId {}, authorId {}: ", commentDto, itemId, authorId);
        return itemClient.createComment(authorId, itemId, commentDto);
    }
}