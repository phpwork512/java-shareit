package ru.practicum.shareit.booking;

import lombok.Data;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

import java.time.LocalDate;

/**
 * TODO Sprint add-bookings.
 */
@Data
public class Booking {
    /**
     * id бронирования в системе, уникальное
     */
    private int bookingId;

    /**
     * вещь, которую бронируют
     */
    private Item item;

    /**
     * пользователь, который бронирует
     */
    private User user;

    /**
     * начало периода аренды, с даты
     */
    private LocalDate rentStartDate;

    /**
     * окончание периода аренды, по дату (включительно)
     */
    private LocalDate rentEndDate;

    /**
     * подтверждение бронирования хозяином вещи
     */
    private boolean confirmed;
}