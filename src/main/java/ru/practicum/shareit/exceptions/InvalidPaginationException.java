package ru.practicum.shareit.exceptions;

public class InvalidPaginationException extends RuntimeException {
    public InvalidPaginationException() {
        super();
    }

    public InvalidPaginationException(String message) {
        super(message);
    }

    public InvalidPaginationException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidPaginationException(Throwable cause) {
        super(cause);
    }
}
