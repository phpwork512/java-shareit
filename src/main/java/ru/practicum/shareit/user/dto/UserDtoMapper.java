package ru.practicum.shareit.user.dto;

import ru.practicum.shareit.user.User;

import java.util.ArrayList;
import java.util.List;

/**
 * утилитарный класс для преобразования User <--> UserDto
 */
public class UserDtoMapper {

    private UserDtoMapper() {
    }

    /**
     * преобразовать dto в бизнес-объект User
     *
     * @param userDto объект UserDto
     * @return объект User или null если на входе был null
     */
    public static User toUser(UserDto userDto) {
        if (userDto != null) return new User(userDto.getId(), userDto.getName(), userDto.getEmail());
        else return null;
    }

    /**
     * преобразовать бизнес-объект User в dto
     *
     * @param user объект User
     * @return объект UserDto или null если на входе был null
     */
    public static UserDto toUserDto(User user) {
        if (user != null) return new UserDto(user.getId(), user.getName(), user.getEmail());
        else return null;
    }

    /**
     * преобразовать список бизнес-объектов User в список dto
     *
     * @param userList список объектов User
     * @return список объектов UserDto
     */
    public static List<UserDto> toUserDtoList(List<User> userList) {
        List<UserDto> userDtoList = new ArrayList<>();

        if (userList != null) {
            for (User user : userList) {
                userDtoList.add(toUserDto(user));
            }
        }

        return userDtoList;
    }
}