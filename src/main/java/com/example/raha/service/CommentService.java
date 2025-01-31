package com.example.raha.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.raha.entity.Article;
import com.example.raha.entity.Comment;
import com.example.raha.entity.User;
import com.example.raha.form.CommentForm;
import com.example.raha.repository.ArticleRepository;
import com.example.raha.repository.CommentRepository;
import com.example.raha.repository.UserRepository;

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
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;

    /**
     * コメント情報追加
     * 
     * @param comment コメント情報
     * @return id コメントID（単体テストで用いるため）
     */
    public Integer insert(CommentForm commentForm) {
        Comment comment = new Comment();
        User user = userRepository.findById(commentForm.getUserId()).orElse(null);
        Article article = articleRepository.findById(commentForm.getArticleId()).orElse(null);
        if (user == null || article == null) {
            return null;
        }
        comment.setArticle(article);
        comment.setUser(user);
        comment.setContent(commentForm.getContent());
        Comment savedComment = commentRepository.save(comment);
        return savedComment.getCommentId();
    }

    /**
     * コメント情報更新
     * 
     * @param comment   コメント情報
     * @param commentId 更新するコメントID
     */
    public void update(CommentForm comment, Integer commentId, Integer userId) {
        commentRepository.update(comment.getContent(), commentId, userId);
    }

    /**
     * コメント情報削除
     * 
     * @param commentId コメントID
     */
    public void delete(Integer commentId, Integer userId) {
        commentRepository.deleteByCommentIdAndUserUserId(commentId, userId);
    }
}
