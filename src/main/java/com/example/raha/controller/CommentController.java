package com.example.raha.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.raha.form.CommentForm;
import com.example.raha.service.CommentService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;


/**
 * コメントに関するコントローラークラス
 * 
 * @author K.Kawachino
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    /**
     * コメントの登録
     * 
     * @param comment
     */
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void insertComment(@RequestBody CommentForm comment) {

        commentService.insert(comment);
    }

    /**
     * コメントの更新
     * 
     * @param comment コメント情報
     * @param commentId 更新するコメントID
     */
    @PutMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateComment(@RequestBody CommentForm comment, @PathVariable Integer commentId) {
        commentService.update(comment, commentId);
    }
    /**
     * コメントの削除
     * 
     * @param commentId
     */
    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable Integer commentId) {
        commentService.delete(commentId);
    }
}
