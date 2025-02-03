package com.example.raha.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * コメントのDTO
 * @author T.hosoda
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private Integer commentId;
    private String content;
    private UserDto user;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
