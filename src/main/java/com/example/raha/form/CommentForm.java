package com.example.raha.form;

import lombok.Data;

/**
 * コメント情報フォーム
 * 
 * @author K.Kawachino
 */
@Data
public class CommentForm {

    private Integer commentId;
    private String content;
    private Integer userId;
    private Integer articleId;
}
