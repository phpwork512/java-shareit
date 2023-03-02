package ru.practicum.shareit.exceptions;

public class ItemRequestNotFoundException extends RuntimeException {
    public ItemRequestNotFoundException() {
        super();
    }

    public ItemRequestNotFoundException(String message) {
        super(message);
    }

    public ItemRequestNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ItemRequestNotFoundException(Throwable cause) {
        super(cause);
    }
}
