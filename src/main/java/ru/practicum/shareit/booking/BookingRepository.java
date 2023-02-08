package ru.practicum.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.shareit.booking.enums.BookingStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByBooker_idOrderByRentStartDateDesc(long bookerId);

    List<Booking> findByBooker_idAndStatusOrderByRentStartDateDesc(long bookerId, BookingStatus rejected);

    @Query("select b from Booking b join Item i on b.item.id = i.id where i.ownerId = ?1 order by b.rentStartDate desc")
    List<Booking> findBookingsOfItemsByOwnerId(long ownerId);

    @Query("select b from Booking b join Item i on b.item.id = i.id where i.ownerId = ?1 and b.status = ?2 order by b.rentStartDate desc")
    List<Booking> findBookingsOfItemsByOwnerIdAndStatus(long ownerId, BookingStatus status);

    @Query("select b from Booking b join Item i on b.item.id = i.id where i.ownerId = ?1 and b.rentStartDate > ?2 order by b.rentStartDate desc")
    List<Booking> findBookingsOfItemsByOwnerIdInFuture(long ownerId, LocalDateTime now);

    @Query("select b from Booking b join Item i on b.item.id = i.id where i.ownerId = ?1 and b.rentEndDate < ?2 order by b.rentStartDate desc")
    List<Booking> findBookingsOfItemsByOwnerIdInPast(long ownerId, LocalDateTime now);

    @Query("select b from Booking b join Item i on b.item.id = i.id where i.ownerId = ?1 and b.rentStartDate < ?2 and b.rentEndDate > ?2 order by b.rentStartDate desc")
    List<Booking> findBookingsOfItemsByOwnerIdInCurrent(long ownerId, LocalDateTime now);

    List<Booking> findByBooker_idAndRentStartDateAfterOrderByRentStartDateDesc(long bookerId, LocalDateTime now);

    List<Booking> findByBooker_idAndRentStartDateBeforeAndRentEndDateAfterOrderByRentStartDateDesc(long bookerId, LocalDateTime now, LocalDateTime now1);

    List<Booking> findByBooker_idAndRentEndDateBeforeOrderByRentStartDateDesc(long bookerId, LocalDateTime now);

    List<Booking> findByItem_idIn(List<Long> itemIdList);

    List<Booking> findByItem_idAndBooker_idAndStatusAndRentEndDateIsBefore(long item_id, long author_id, BookingStatus approved, LocalDateTime now);
}