package ru.practicum.shareit.item.dto;

import ru.practicum.shareit.item.comment.Comment;

import java.util.ArrayList;
import java.util.List;

public class CommentDtoMapper {
    private CommentDtoMapper() {
    }

    public static List<CommentDto> toCommentDtoList(List<Comment> commentList) {
        List<CommentDto> commentDtoList = new ArrayList<>();
        if (commentList != null) {
            for (Comment comment : commentList) {
                commentDtoList.add(new CommentDto(comment.getId(), comment.getText(), comment.getAuthor().getName(), comment.getCreated()));
            }
        }
        return commentDtoList;
    }

    public static CommentDto toCommentDto(Comment comment) {
        return new CommentDto(comment.getId(), comment.getText(), comment.getAuthor().getName(), comment.getCreated());
    }
}