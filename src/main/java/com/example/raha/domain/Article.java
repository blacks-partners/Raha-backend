package com.example.raha.domain;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article {

    private Integer articleId;
    private String title;
    private String content;
    private User user;
    private List<Comment> commentList;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
