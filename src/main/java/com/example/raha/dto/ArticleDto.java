package com.example.raha.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 記事のDTO
 * @author T.hosoda
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDto {
    private Integer articleId;
    private String title;
    private String content;
    private UserDto user;
    private String createdAt;
    private String updatedAt;
}
