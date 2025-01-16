package com.example.raha.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    private Integer commentId;
    private User user;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer articleId;
}
