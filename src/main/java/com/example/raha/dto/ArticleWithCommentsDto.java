package com.example.raha.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * コメント情報付き記事のDTO
 * @author T.hosoda
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleWithCommentsDto {
    private Integer articleId;
    private String title;
    private String content;
    private UserDto user;
    private List<CommentDto> comments;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
