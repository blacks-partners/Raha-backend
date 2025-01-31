package com.example.raha.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.raha.entity.Comment;

/**
 * コメントに関するリポジトリクラス
 * 
 * @author K.Kawachino
 */
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    @Modifying
    @Query("""
            UPDATE Comment c
            SET c.content = :content,
            c.updatedAt = CURRENT_TIMESTAMP
            WHERE c.commentId = :commentId
            AND c.user.userId = :userId
            """)
    public int update(String content, Integer commentId, Integer userId);

    public int deleteByCommentIdAndUserUserId(Integer commentId, Integer userId);
}
