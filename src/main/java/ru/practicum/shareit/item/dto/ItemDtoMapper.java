package ru.practicum.shareit.item.dto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.dto.BookingDtoMapper;
import ru.practicum.shareit.item.model.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * утилитарный класс для преобразования Item <--> ItemDto
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ItemDtoMapper {
    /**
     * преобразовать ItemDto в Item
     *
     * @param itemDto объект ItemDto
     * @return объект Item или если itemDto был null, то возвращает тоже null
     */
    public static Item toItem(ItemDto itemDto) {
        if (itemDto != null) {
            return Item.builder()
                .id(itemDto.getId())
                .name(itemDto.getName())
                .description(itemDto.getDescription())
                .available(itemDto.getAvailable()).build();
        } else {
            return null;
        }
    }

    /**
     * преобразовать Item в ItemDto
     *
     * @param item объект Item
     * @return объект ItemDto или если item был null, то возвращает тоже null
     */
    public static ItemDto toItemDto(Item item) {
        if (item != null) {
            return ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .lastBooking(BookingDtoMapper.toBookingShortDto(item.getLastBooking()))
                .nextBooking(BookingDtoMapper.toBookingShortDto(item.getNextBooking()))
                .comments(CommentDtoMapper.toCommentDtoList(item.getComments())).build();
        } else {
            return null;
        }
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