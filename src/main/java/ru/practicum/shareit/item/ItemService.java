package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ItemService {
    private final ItemStorage itemStorage;

    /**
     * получить данные вещи по id
     *
     * @param itemId id вещи
     * @return объект типа Item
     */
    public Item getById(int itemId) {
        return itemStorage.getById(itemId);
    }

    /**
     * получить список вещей, принадлежащих пользователю с указанным id
     *
     * @param ownerId id пользователя
     * @return список объектов типа Item
     */
    public List<Item> getOwnedItemsList(int ownerId) {
        return itemStorage.getOwnedItemsList(ownerId);
    }

    /**
     * сохранить новую вещь в хранилище, присвоить уникальный id
     *
     * @param item    заполненный объект Item
     * @param ownerId id пользователя, который будет указан владельцем вещи
     * @return заполненный объект Item
     */
    public Item create(Item item, int ownerId) {
        return itemStorage.create(item, ownerId);
    }

    /**
     * изменить данные вещи с указанным id
     *
     * @param item    заполненный объект Item
     * @param ownerId id пользователя - владельца вещи
     * @return заполненный объект Item
     */
    public Item update(Item item, int ownerId) {
        return itemStorage.update(item, ownerId);
    }

    /**
     * удалить вещь с указанным id из хранилища
     *
     * @param itemId  id вещи
     * @param ownerId id пользователя - владельца вещи
     */
    void delete(int itemId, int ownerId) {
        itemStorage.delete(itemId, ownerId);
    }

    /**
     * поиск строки в названии или описании вещей
     *
     * @param text строка поиска
     * @return список объектов Item, которые содержат искомую строку в названиях или описаниях
     */
    public List<Item> search(String text) {
        return itemStorage.search(text);
    }
}