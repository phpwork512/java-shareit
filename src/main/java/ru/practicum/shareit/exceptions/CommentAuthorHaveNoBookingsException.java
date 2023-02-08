package ru.practicum.shareit.exceptions;

public class CommentAuthorHaveNoBookingsException extends RuntimeException {
    public CommentAuthorHaveNoBookingsException() {
        super();
    }

    public CommentAuthorHaveNoBookingsException(String message) {
        super(message);
    }

    public CommentAuthorHaveNoBookingsException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommentAuthorHaveNoBookingsException(Throwable cause) {
        super(cause);
    }
}