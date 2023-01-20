package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
@Slf4j
public class ItemController {
    private final ItemService itemService;
    private final String xHeaderName = "X-Sharer-User-Id";

    /**
     * сохранить новую вещь в хранилище, присвоить уникальный id
     *
     * @param item    заполненный валидированный объект Item
     * @param ownerId id пользователя, который будет указан владельцем вещи
     * @return заполненный объект ItemDto
     * @throws ValidationException если указан ошибочный id пользователя-владельца (<=0)
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemDto create(@Valid @RequestBody Item item, @RequestHeader(value = xHeaderName, defaultValue = "0") int ownerId)
            throws ValidationException {
        log.info("Create item, owner {}: " + item.toString(), ownerId);
        if (ownerId <= 0) {
            throw new ValidationException("Указан ошибочный id владельца");
        }

        return ItemDtoMapper.toItemDto(itemService.create(item, ownerId));
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
    @ResponseStatus(HttpStatus.OK)
    public ItemDto update(@PathVariable int itemId, @Valid @RequestBody ItemDto itemDto, @RequestHeader(value = xHeaderName, defaultValue = "0") int ownerId)
            throws ValidationException {
        log.info("Update item {}, ownerId {}: " + itemDto, itemId, ownerId);
        if (ownerId <= 0) {
            throw new ValidationException("Указан ошибочный id владельца");
        }

        itemDto.setId(itemId);
        Item item = ItemDtoMapper.toItem(itemDto);

        return ItemDtoMapper.toItemDto(itemService.update(item, ownerId));
    }

    /**
     * получить список вещей, принадлежащих пользователю с указанным id
     *
     * @param ownerId id пользователя
     * @return список объектов типа ItemDto
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ItemDto> getOwnedItemsList(@RequestHeader(value = xHeaderName, defaultValue = "0") int ownerId) {
        log.info("Get owned items list, ownerId {}", ownerId);
        if (ownerId <= 0) {
            throw new ValidationException("Указан ошибочный id владельца");
        }

        return ItemDtoMapper.toItemDtoList(itemService.getOwnedItemsList(ownerId));
    }

    /**
     * получить данные вещи по id
     *
     * @param itemId id вещи
     * @return объект типа ItemDto
     */
    @GetMapping("/{itemId}")
    @ResponseStatus(HttpStatus.OK)
    public ItemDto get(@PathVariable int itemId) {
        log.info("Get itemId {}", itemId);
        return ItemDtoMapper.toItemDto(itemService.getById(itemId));
    }

    /**
     * поиск строки в названии или описании вещей
     *
     * @param text строка поиска
     * @return список объектов Item, которые содержат искомую строку в названиях или описаниях
     */
    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<ItemDto> search(@RequestParam(defaultValue = "") String text) {
        log.info("Search text '{}'", text);
        if (!text.isBlank()) {
            return ItemDtoMapper.toItemDtoList(itemService.search(text));
        } else {
            return List.of();
        }
    }

    /**
     * удалить вещь с указанным id из хранилища
     *
     * @param itemId  id вещи
     * @param ownerId id пользователя - владельца вещи
     */
    @DeleteMapping("/{itemId}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable int itemId, @RequestHeader(value = xHeaderName, defaultValue = "0") int ownerId) {
        log.info("Delete itemId {}, ownerId {}", itemId, ownerId);
        if (ownerId <= 0) {
            throw new ValidationException("Указан ошибочный id владельца");
        }

        itemService.delete(itemId, ownerId);
    }

    /**
     * утилитарный класс для преобразования Item <--> ItemDto
     */
    private static class ItemDtoMapper {
        /**
         * преобразовать ItemDto в Item
         *
         * @param itemDto объект ItemDto
         * @return объект Item или если itemDto был null, то возвращает тоже null
         */
        public static Item toItem(ItemDto itemDto) {
            if (itemDto != null)
                return new Item(itemDto.getId(), 0, itemDto.getName(), itemDto.getDescription(), itemDto.getAvailable());
            else return null;
        }

        /**
         * преобразовать Item в ItemDto
         *
         * @param item объект Item
         * @return объект ItemDto или если item был null, то возвращает тоже null
         */
        public static ItemDto toItemDto(Item item) {
            if (item != null)
                return new ItemDto(item.getId(), item.getName(), item.getDescription(), item.getAvailable());
            else return null;
        }

        /**
         * преобразовать список Item в список ItemDto
         *
         * @param itemList список объектов Item
         * @return список объектов ItemDto
         */
        public static List<ItemDto> toItemDtoList(List<Item> itemList) {
            List<ItemDto> itemDtoList = new ArrayList<>();

            if (itemList != null) {
                for (Item item : itemList) {
                    itemDtoList.add(toItemDto(item));
                }
            }

            return itemDtoList;
        }
    }
}