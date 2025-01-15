package com.example.raha.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentForm {

    private String content;
    private Integer userId;
    private Integer articleId;
}
