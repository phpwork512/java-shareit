package ru.practicum.shareit.exceptions;

public class BookingUnsupportedStatusException extends RuntimeException {
    public BookingUnsupportedStatusException() {
        super();
    }

    public BookingUnsupportedStatusException(String message) {
        super(message);
    }

    public BookingUnsupportedStatusException(String message, Throwable cause) {
        super(message, cause);
    }

    public BookingUnsupportedStatusException(Throwable cause) {
        super(cause);
    }
}