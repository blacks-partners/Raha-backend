package com.example.raha.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import com.example.raha.form.CommentForm;

@JdbcTest
@Sql("/test-schemaComment.sql")
@Transactional
@Import(CommentRepository.class)
public class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    private CommentForm createCommentForm() {
        CommentForm comment = new CommentForm();
        comment.setArticleId(10);
        comment.setUserId(10);
        comment.setContent("コメントテスト");
        return comment;
    }
    
    @Test
    void testInsert() {
        CommentForm comment = createCommentForm();
        Integer commentId = commentRepository.insert(comment);
        assertNotNull(commentId);

        // 挿入結果を確認
        String sql = "SELECT COUNT(*) FROM comments WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("id", commentId);
        Integer count = jdbcTemplate.queryForObject(sql, params, Integer.class);
        assertNotNull(count);
        assertTrue(count > 0);
    }

    @Test
    void testUpdate() {
        CommentForm comment = createCommentForm();
        Integer commentId = commentRepository.insert(comment);

        comment.setContent("更新されたコメント");
        commentRepository.update(comment, commentId);

        String sql = "SELECT content FROM comments WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("id", commentId);
        String updatedContent = jdbcTemplate.queryForObject(sql, params, String.class);
        assertTrue(updatedContent.equals("更新されたコメント"));
    }

    @Test
    void testDelete() {
        CommentForm comment = createCommentForm();
        Integer commentId = commentRepository.insert(comment);

        commentRepository.delete(commentId, comment.getUserId());

        String sql = "SELECT COUNT(*) FROM comments WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("id", commentId);
        Integer count = jdbcTemplate.queryForObject(sql, params, Integer.class);
        assertTrue(count == 0);
    }
}
