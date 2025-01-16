package com.example.raha.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleForm {

    private String title;
    private String content;
    private Integer userId;

}
