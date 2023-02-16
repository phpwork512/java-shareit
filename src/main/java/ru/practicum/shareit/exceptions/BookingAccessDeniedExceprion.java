package ru.practicum.shareit.exceptions;

public class BookingAccessDeniedExceprion extends RuntimeException {
    public BookingAccessDeniedExceprion() {
        super();
    }

    public BookingAccessDeniedExceprion(String message) {
        super(message);
    }

    public BookingAccessDeniedExceprion(String message, Throwable cause) {
        super(message, cause);
    }

    public BookingAccessDeniedExceprion(Throwable cause) {
        super(cause);
    }
}