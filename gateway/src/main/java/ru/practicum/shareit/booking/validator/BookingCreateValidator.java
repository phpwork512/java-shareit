package ru.practicum.shareit.booking.validator;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.dto.BookingCreateRequest;

import javax.validation.ValidationException;

/**
 * Класс для дополнительной валидации данных бронирования
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BookingCreateValidator {
    public static void validate(BookingCreateRequest bookingCreateRequest) throws ValidationException {
        //дата начала должна быть ранее даты конца
        if (!bookingCreateRequest.getStart().isBefore(bookingCreateRequest.getEnd())) {
            throw new ValidationException("Дата начала бронирования должна быть раньше даты окончания");
        }
    }
}