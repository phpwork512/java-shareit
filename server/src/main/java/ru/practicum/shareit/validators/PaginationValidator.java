package ru.practicum.shareit.validators;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PaginationValidator {
    public static Pageable validate(int from, int size) {
        int pageNumber = from / size;
        return PageRequest.of(pageNumber, size);
    }
}
