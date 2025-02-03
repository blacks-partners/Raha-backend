package com.example.raha.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ユーザーのDTO
 * @author T.hosoda
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Integer userId;
    private String name;
    private String introduction;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
