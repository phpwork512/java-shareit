package ru.practicum.shareit.booking.dto;

import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.item.dto.ItemDtoMapper;
import ru.practicum.shareit.user.dto.UserDtoMapper;

import java.util.ArrayList;
import java.util.List;

public class BookingDtoMapper {
    private BookingDtoMapper() {
    }

    public static BookingDto toBookingDto(Booking booking) {
        if (booking != null) {
            return new BookingDto(booking.getId(),
                    ItemDtoMapper.toItemDto(booking.getItem()),
                    UserDtoMapper.toUserDto(booking.getBooker()),
                    booking.getRentStartDate(),
                    booking.getRentEndDate(),
                    booking.getStatus());
        } else return null;
    }

    public static BookingShortDto toBookingShortDto(Booking booking) {
        if (booking != null) {
            return new BookingShortDto(booking.getId(), booking.getBooker().getId(), booking.getRentStartDate(), booking.getRentEndDate());
        } else return null;
    }

    public static List<BookingDto> toBookingDtoList(List<Booking> bookingList) {
        List<BookingDto> bookingDtoList = new ArrayList<>();

        if (bookingList != null) {
            for (Booking booking : bookingList) {
                bookingDtoList.add(toBookingDto(booking));
            }
        }

        return bookingDtoList;
    }
}