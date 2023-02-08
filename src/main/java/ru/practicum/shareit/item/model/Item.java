package ru.practicum.shareit.item.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.item.comment.Comment;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="items")
public class Item {

    /**
     * id вещи в системе, уникальное
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="item_id", nullable = false)
    private long id;

    /**
     * id пользователя-владельца
     */
    @Column(name="owner_id", nullable = false)
    private long ownerId;

    /**
     * название вещи
     */
    @NotBlank(message = "Название вещи не может быть пустым")
    @Column(nullable = false)
    private String name;

    /**
     * описание вещи
     */
    @NotBlank(message = "Описание вещи не может быть пустым")
    @Column(nullable = false)
    private String description;

    /**
     * статус вещи
     * true - можно брать в аренду
     * false - нельзя брать в аренду
     */
    @NotNull
    @Column(nullable = false)
    private Boolean available;

    /**
     * предыдущее бронирование вещи
     */
    @Transient
    private Booking lastBooking;

    /**
     * следующее бронирование вещи
     */
    @Transient
    private Booking nextBooking;

    /**
     * список отзывов к вещи
     */
    @Transient
    private List<Comment> comments;
}