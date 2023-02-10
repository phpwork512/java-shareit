package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingCreateRequest;
import ru.practicum.shareit.exceptions.*;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static ru.practicum.shareit.booking.enums.BookingStatus.*;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final UserService userService;
    private final ItemService itemService;

    /**
     * Найти бронирование по id. Ищется бронирование либо созданное пользователем, либо забронирована вещь пользователя
     *
     * @param bookingId id бронирования
     * @param userId    id пользователя
     * @return объект типа Booking
     * @throws BookingNotFoundException если бронирование не существует, или не принадлежит пользователю, или забронированная вещь не принадлежит пользователю
     */
    public Booking getById(long bookingId, long userId) throws BookingNotFoundException {
        Optional<Booking> optionalBooking = bookingRepository.findById(bookingId);
        if (optionalBooking.isEmpty()) {
            throw new BookingNotFoundException("Бронирование " + bookingId + " не найдено");
        } else {
            Booking booking = optionalBooking.get();
            if (booking.getBooker().getId() == userId || booking.getItem().getOwnerId() == userId) {
                return booking;
            } else {
                throw new BookingNotFoundException("Бронирование " + bookingId + " не найдено");
            }
        }
    }

    /**
     * Создать запись о новом бронировании
     *
     * @param bookingCreateRequest объект с данными о новом бронировании
     * @param bookerId             id пользователя, который делает запрос
     * @return объект типа Booking
     * @throws BookingItemUnavailableExceprion если запрашиваемая вещь недоступна для бронирования
     * @throws ItemNotFoundException если бронируется своя же вещь
     */
    public Booking create(BookingCreateRequest bookingCreateRequest, long bookerId) throws BookingItemUnavailableExceprion, ItemNotFoundException {
        User booker = userService.getById(bookerId);
        Item item = itemService.getById(bookingCreateRequest.getItemId(), 0);
        if (!item.getAvailable()) {
            throw new BookingItemUnavailableExceprion("Вещь с id = " + item.getId() + " недоступна для бронирования");
        }

        if (item.getOwnerId() == bookerId) {
            throw new ItemNotFoundException("Вещь с id = " + item.getId() + " не найдена");
        }

        Booking booking = new Booking(0, item, booker, bookingCreateRequest.getStart(), bookingCreateRequest.getEnd(), WAITING);
        booking = bookingRepository.save(booking);
        updateItemBookings(item, booking);
        return booking;
    }

    private void updateItemBookings(Item item, Booking booking) {

    }

    /**
     * Подтверждение бронирования владельцем вещи
     *
     * @param bookingId id бронирования
     * @param approved  вид действия: true - подтвердить, false - отклонить
     * @param ownerId   id пользователя, который делает запрос
     * @return объект типа Booking
     * @throws BookingAlreadyApprovedException бронирование не находится в состоянии WAITING
     * @throws BookingNotFoundException        вещь не принадлежит пользователю с ownerId
     */
    public Booking approve(long bookingId, boolean approved, long ownerId) throws BookingAlreadyApprovedException, BookingNotFoundException {
        Booking booking = getById(bookingId, ownerId);
        if (booking.getItem().getOwnerId() == ownerId) {
            if (booking.getStatus() == WAITING) {
                booking.setStatus(approved ? APPROVED : REJECTED);
                return bookingRepository.save(booking);
            } else {
                throw new BookingAlreadyApprovedException("Бронирование " + bookingId + " имеет неверный статус для подтверждения");
            }
        } else {
            throw new BookingNotFoundException("Бронирование " + bookingId + " не найдено");
        }
    }

    /**
     * Вернуть список бронирований, созданных пользователем
     *
     * @param bookerId id пользователя
     * @param state    вид фильтрации данных: по умолчанию равен ALL, может принимать значения CURRENT, PAST, FUTURE, WAITING, REJECTED
     * @return список объектов Booking
     * @throws BookingUnsupportedStatusException если указан неверный вид фильтрации
     */
    public List<Booking> getBookingsByBookerId(long bookerId, String state) throws BookingUnsupportedStatusException {
        userService.getById(bookerId);

        if ("CURRENT".equals(state)) {
            return bookingRepository.findByBooker_idAndRentStartDateBeforeAndRentEndDateAfterOrderByRentStartDateDesc(bookerId, LocalDateTime.now(), LocalDateTime.now());
        }

        if ("PAST".equals(state)) {
            return bookingRepository.findByBooker_idAndRentEndDateBeforeOrderByRentStartDateDesc(bookerId, LocalDateTime.now());
        }

        if ("FUTURE".equals(state)) {
            return bookingRepository.findByBooker_idAndRentStartDateAfterOrderByRentStartDateDesc(bookerId, LocalDateTime.now());
        }

        if ("WAITING".equals(state)) {
            return bookingRepository.findByBooker_idAndStatusOrderByRentStartDateDesc(bookerId, WAITING);
        }

        if ("REJECTED".equals(state)) {
            return bookingRepository.findByBooker_idAndStatusOrderByRentStartDateDesc(bookerId, REJECTED);
        }

        //возвращаем все бронирования пользователя
        if ("ALL".equals(state)) {
            return bookingRepository.findByBooker_idOrderByRentStartDateDesc(bookerId);
        }

        throw new BookingUnsupportedStatusException("Unknown state: " + state);
    }

    /**
     * Вернуть список бронирований вещей пользователя
     *
     * @param ownerId id пользователя
     * @param state   вид фильтрации данных: по умолчанию равен ALL, может принимать значения CURRENT, PAST, FUTURE, WAITING, REJECTED
     * @return список объектов Booking
     * @throws BookingUnsupportedStatusException если указан неверный вид фильтрации
     */
    public List<Booking> getBookingsByOwnerId(long ownerId, String state) {
        userService.getById(ownerId);

        if ("CURRENT".equals(state)) {
            return bookingRepository.findBookingsOfItemsByOwnerIdInCurrent(ownerId, LocalDateTime.now());
        }

        if ("PAST".equals(state)) {
            return bookingRepository.findBookingsOfItemsByOwnerIdInPast(ownerId, LocalDateTime.now());
        }

        if ("FUTURE".equals(state)) {
            return bookingRepository.findBookingsOfItemsByOwnerIdInFuture(ownerId, LocalDateTime.now());
        }

        if ("WAITING".equals(state)) {
            return bookingRepository.findBookingsOfItemsByOwnerIdAndStatus(ownerId, WAITING);
        }

        if ("REJECTED".equals(state)) {
            return bookingRepository.findBookingsOfItemsByOwnerIdAndStatus(ownerId, REJECTED);
        }

        //возвращаем все бронирования всех вещей пользователя
        if ("ALL".equals(state)) {
            return bookingRepository.findBookingsOfItemsByOwnerId(ownerId);
        }

        throw new BookingUnsupportedStatusException("Unknown state: " + state);
    }
}