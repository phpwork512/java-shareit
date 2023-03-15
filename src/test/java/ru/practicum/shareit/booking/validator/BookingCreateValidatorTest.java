package ru.practicum.shareit.booking.validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.booking.dto.BookingCreateRequest;

import javax.validation.ValidationException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;

class BookingCreateValidatorTest {
    @Test
    void validateNormalWayTest() {
        BookingCreateRequest bookingCreateRequest = BookingCreateRequest.builder().itemId(1)
                .start(LocalDateTime.now().plusHours(1))
                .end(LocalDateTime.now().plusHours(2)).build();

        BookingCreateValidator.validate(bookingCreateRequest);
    }

    @Test
    void validateStartMustBeforeEndTest() {
        BookingCreateRequest bookingCreateRequest = BookingCreateRequest.builder().itemId(1)
                .start(LocalDateTime.now().plusHours(3))
                .end(LocalDateTime.now().plusHours(2)).build();

        final ValidationException exception = assertThrows(
                ValidationException.class,
                () -> BookingCreateValidator.validate(bookingCreateRequest));

        Assertions.assertEquals("Дата начала бронирования должна быть раньше даты окончания", exception.getMessage());
    }

    @Test
    void validateStartMustBeInFutureTest() {
        BookingCreateRequest bookingCreateRequest = BookingCreateRequest.builder().itemId(1)
                .start(LocalDateTime.now().minusHours(3))
                .end(LocalDateTime.now().plusHours(2)).build();

        final ValidationException exception = assertThrows(
                ValidationException.class,
                () -> BookingCreateValidator.validate(bookingCreateRequest));

        Assertions.assertEquals("Дата начала бронирования должна быть в будущем", exception.getMessage());
    }
}