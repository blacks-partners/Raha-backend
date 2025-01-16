package com.example.raha.service;

import java.util.List;

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
     * @param comment コメント情報
     * @return id コメントID（単体テストで用いるため）
     */
    public Integer insert(CommentForm comment) {
        return commentRepository.insert(comment);
    }

    /**
     * コメント情報取得
     * 
     * @param articleId 記事ID
     * @return comment コメント情報
     */
    public List<Comment> load(Integer articleId) {
        return commentRepository.load(articleId);
    }

    /**
     * コメント情報更新
     * 
     * @param comment コメント情報
     * @param commentId 更新するコメントID
     */
    public void update(CommentForm comment, Integer commentId) {
        commentRepository.update(comment, commentId);
    }
}
