package ru.practicum.shareit.item.dto;

import ru.practicum.shareit.booking.dto.BookingDtoMapper;
import ru.practicum.shareit.item.model.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * утилитарный класс для преобразования Item <--> ItemDto
 */
public class ItemDtoMapper {

    private ItemDtoMapper() {
    }

    /**
     * преобразовать ItemDto в Item
     *
     * @param itemDto объект ItemDto
     * @return объект Item или если itemDto был null, то возвращает тоже null
     */
    public static Item toItem(ItemDto itemDto) {
        if (itemDto != null)
            return new Item(itemDto.getId(),
                    0L,
                    itemDto.getName(),
                    itemDto.getDescription(),
                    itemDto.getAvailable(),
                    null,
                    null,
                    null);
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
            return new ItemDto(item.getId(),
                    item.getName(),
                    item.getDescription(),
                    item.getAvailable(),
                    BookingDtoMapper.toBookingShortDto(item.getLastBooking()),
                    BookingDtoMapper.toBookingShortDto(item.getNextBooking()),
                    CommentDtoMapper.toCommentDtoList(item.getComments()));
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