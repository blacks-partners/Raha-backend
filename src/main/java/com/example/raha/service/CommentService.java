package com.example.raha.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
     * @param comment コメント情報
     * @return id コメントID（単体テストで用いるため）
     */
    public Integer insert(CommentForm comment) {
        return commentRepository.insert(comment);
    }

    /**
     * コメント情報更新
     * 
     * @param comment コメント情報
     * @param commentId 更新するコメントID
     */
    public void update(CommentForm comment, Integer commentId, Integer userId) {
        commentRepository.update(comment, commentId, userId);
    }
    
    /**
     * コメント情報削除
     * 
     * @param commentId コメントID
     */
    public void delete(Integer commentId, Integer userId) {
        commentRepository.delete(commentId, userId);
    }
}
