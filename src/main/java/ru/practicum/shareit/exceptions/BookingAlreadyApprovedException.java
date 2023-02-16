package ru.practicum.shareit.exceptions;

public class BookingAlreadyApprovedException extends RuntimeException {
    public BookingAlreadyApprovedException() {
        super();
    }

    public BookingAlreadyApprovedException(String message) {
        super(message);
    }

    public BookingAlreadyApprovedException(String message, Throwable cause) {
        super(message, cause);
    }

    public BookingAlreadyApprovedException(Throwable cause) {
        super(cause);
    }
}