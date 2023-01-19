package ru.practicum.shareit.item.dto;

import ru.practicum.shareit.item.model.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemDtoMapper {
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
        if (item != null) return new ItemDto(item.getId(), item.getName(), item.getDescription(), item.getAvailable());
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