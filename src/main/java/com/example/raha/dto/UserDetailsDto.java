package com.example.raha.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ユーザー詳細情報のDTO
 * @author T.hosoda
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsDto {
    private Integer userId;
    private String name;
    private String email;
    private String introduction;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
