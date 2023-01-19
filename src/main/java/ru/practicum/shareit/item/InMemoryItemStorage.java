package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.exceptions.InvalidItemOwnerException;
import ru.practicum.shareit.exceptions.ItemAccessDeniedException;
import ru.practicum.shareit.exceptions.ItemNotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserStorage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class InMemoryItemStorage implements ItemStorage {

    private final UserStorage userStorage;

    /**
     * счетчик для присваивания уникальных id
     */
    private int nextId = 0;

    /**
     * хранилище объектов Item
     */
    private final Map<Integer, Item> storageMap = new HashMap<>();

    /**
     * получить данные вещи по id
     *
     * @param itemId id вещи
     * @return объект типа Item
     * @throws ItemNotFoundException если вещь с таким id не найдена в хранилище
     */
    @Override
    public Item getById(int itemId) throws ItemNotFoundException {
        if (storageMap.containsKey(itemId)) {
            return storageMap.get(itemId);
        } else {
            throw new ItemNotFoundException("Вещь не найдена");
        }
    }

    /**
     * получить список вещей, принадлежащих пользователю с указанным id
     *
     * @param ownerId id пользователя
     * @return список объектов типа Item
     */
    @Override
    public List<Item> getOwnedItemsList(int ownerId) {
        return storageMap.values().stream()
                .filter(item -> item.getOwnerId() == ownerId)
                .collect(Collectors.toList());
    }

    /**
     * сохранить новую вещь в хранилище, присвоить уникальный id
     *
     * @param item    заполненный объект Item
     * @param ownerId id пользователя, который будет указан владельцем вещи
     * @return заполненный объект Item
     */
    @Override
    public Item create(Item item, int ownerId) throws InvalidItemOwnerException {
        if (userStorage.getById(ownerId) == null) {
            throw new InvalidItemOwnerException("Владелец с указанным id не существует");
        } else {
            if (item != null) {
                item.setId(++nextId);
                item.setOwnerId(ownerId);
                storageMap.put(item.getId(), item);
            }
        }

        return item;
    }

    /**
     * изменить данные вещи с указанным id
     *
     * @param item    заполненный объект Item
     * @param ownerId id пользователя - владельца вещи
     * @return заполненный объект Item
     */
    @Override
    public Item update(Item item, int ownerId) throws ItemAccessDeniedException, ItemNotFoundException {
        if (item != null) {
            Item storageItem = storageMap.get(item.getId());
            if (storageItem != null) {
                if (storageItem.getOwnerId() == ownerId) {
                    if (item.getName() != null && !item.getName().isBlank()) storageItem.setName(item.getName());
                    if (item.getDescription() != null && !item.getDescription().isBlank())
                        storageItem.setDescription(item.getDescription());
                    if (item.getAvailable() != null) storageItem.setAvailable(item.getAvailable());
                } else {
                    throw new ItemAccessDeniedException("Вещь принадлежит другому пользователю");
                }
            } else {
                throw new ItemNotFoundException("Вещь не найдена");
            }

            return storageItem;
        }

        return item;
    }

    /**
     * удалить вещь с указанным id из хранилища
     *
     * @param itemId  id вещи
     * @param ownerId id пользователя - владельца вещи
     */
    @Override
    public void delete(int itemId, int ownerId) throws ItemAccessDeniedException, ItemNotFoundException {
        Item storageItem = storageMap.get(itemId);
        if (storageItem != null) {
            if (storageItem.getOwnerId() == ownerId) {
                storageMap.remove(itemId);
            } else {
                throw new ItemAccessDeniedException("Вещь принадлежит другому пользователю");
            }
        } else {
            throw new ItemNotFoundException("Вещь не найдена");
        }
    }

    /**
     * функция для поиска заданного текста в полях название и описание вещи
     *
     * @param item объект Item
     * @param text текст для поиска
     * @return true если текст найден, false если не найден
     */
    private static boolean searchFunction(Item item, String text) {
        return item.getName().concat(" ".concat(item.getDescription()))
                .toLowerCase()
                .contains(text.toLowerCase());
    }

    /**
     * поиск строки в названии или описании вещей
     *
     * @param text строка поиска
     * @return список объектов Item, которые содержат искомую строку в названиях или описаниях
     */
    @Override
    public List<Item> search(String text) {
        return storageMap.values().stream()
                .filter(Item::getAvailable)
                .filter(item -> searchFunction(item, text))
                .collect(Collectors.toList());
    }
}