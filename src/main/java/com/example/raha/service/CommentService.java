package com.example.raha.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.raha.domain.Comment;
import com.example.raha.form.CommentForm;
import com.example.raha.repository.CommentRepository;

import lombok.RequiredArgsConstructor;

/**
 * コメントに関するサービスクラス
 * 
 * @author K.Kawachino
 */
@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    /**
     * コメント情報追加
     * 
     * @param comment
     * @return
     */
    public void insert(CommentForm comment) {
        commentRepository.insert(comment);
    }
}
