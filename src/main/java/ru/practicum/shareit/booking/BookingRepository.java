package ru.practicum.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.booking.enums.BookingStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByBooker_idOrderByRentStartDateDesc(long bookerId);

    List<Booking> findByBooker_idAndStatusOrderByRentStartDateDesc(long bookerId, BookingStatus rejected);

    @Query("SELECT b FROM Booking b JOIN Item i ON b.item.id = i.id WHERE i.ownerId = ?1 ORDER BY b.rentStartDate DESC")
    List<Booking> findBookingsOfItemsByOwnerId(long ownerId);

    @Query("SELECT b FROM Booking b JOIN Item i ON b.item.id = i.id WHERE i.ownerId = ?1 AND b.status = ?2 ORDER BY b.rentStartDate DESC")
    List<Booking> findBookingsOfItemsByOwnerIdAndStatus(long ownerId, BookingStatus status);

    @Query("SELECT b FROM Booking b JOIN Item i ON b.item.id = i.id WHERE i.ownerId = ?1 AND b.rentStartDate > ?2 ORDER BY b.rentStartDate DESC")
    List<Booking> findBookingsOfItemsByOwnerIdInFuture(long ownerId, LocalDateTime now);

    @Query("SELECT b FROM Booking b JOIN Item i ON b.item.id = i.id WHERE i.ownerId = ?1 AND b.rentEndDate < ?2 ORDER BY b.rentStartDate DESC")
    List<Booking> findBookingsOfItemsByOwnerIdInPast(long ownerId, LocalDateTime now);

    @Query("SELECT b FROM Booking b JOIN Item i ON b.item.id = i.id WHERE i.ownerId = ?1 AND b.rentStartDate < ?2 AND b.rentEndDate > ?2 ORDER BY b.rentStartDate DESC")
    List<Booking> findBookingsOfItemsByOwnerIdInCurrent(long ownerId, LocalDateTime now);

    List<Booking> findByBooker_idAndRentStartDateAfterOrderByRentStartDateDesc(long bookerId, LocalDateTime now);

    List<Booking> findByBooker_idAndRentStartDateBeforeAndRentEndDateAfterOrderByRentStartDateDesc(long bookerId, LocalDateTime now, LocalDateTime now1);

    List<Booking> findByBooker_idAndRentEndDateBeforeOrderByRentStartDateDesc(long bookerId, LocalDateTime now);

    List<Booking> findByItem_idIn(List<Long> itemIdList);

    List<Booking> findByItem_idAndBooker_idAndStatusAndRentEndDateIsBefore(long itemId, long authorId, BookingStatus approved, LocalDateTime now);
}