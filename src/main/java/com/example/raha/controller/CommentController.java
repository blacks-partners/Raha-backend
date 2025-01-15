package com.example.raha.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.raha.domain.Comment;
import com.example.raha.service.CommentService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


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

    @PostMapping("/insert")
    public Comment InsertComment(@RequestBody Comment comment) {
        return commentService.insert(comment);
    }
}
