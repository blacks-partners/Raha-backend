package com.example.rafa.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Integer id;
    private String name;
    private String email;
    private String password;
    private String introduction;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
