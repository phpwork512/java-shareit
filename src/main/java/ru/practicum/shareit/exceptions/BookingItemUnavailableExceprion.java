package ru.practicum.shareit.exceptions;

public class BookingItemUnavailableExceprion extends RuntimeException {
    public BookingItemUnavailableExceprion() {
        super();
    }

    public BookingItemUnavailableExceprion(String message) {
        super(message);
    }

    public BookingItemUnavailableExceprion(String message, Throwable cause) {
        super(message, cause);
    }

    public BookingItemUnavailableExceprion(Throwable cause) {
        super(cause);
    }
}