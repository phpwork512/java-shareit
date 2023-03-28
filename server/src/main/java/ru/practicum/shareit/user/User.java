package ru.practicum.shareit.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
@Builder
public class User {
    /**
     * id пользователя в системе, уникальное
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private long id;

    /**
     * имя пользователя
     */
    @Column(nullable = false)
    private String name;

    /**
     * email пользователя, должен быть уникальным
     */
    @Column(nullable = false, unique = true)
    private String email;
}