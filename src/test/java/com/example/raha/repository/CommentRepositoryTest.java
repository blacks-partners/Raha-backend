package com.example.raha.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import com.example.raha.form.CommentForm;

@SpringBootTest
@Import(CommentRepository.class)
@Sql("/TestSql.sql")
@Transactional
public class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    private CommentForm createCommentForm() {
        String sqlArticleId = "SELECT id FROM articles LIMIT 1";
        Integer articleId = jdbcTemplate.queryForObject(sqlArticleId, new MapSqlParameterSource(), Integer.class);
        String sqlUserId = "SELECT id FROM users LIMIT 1";
        Integer userId = jdbcTemplate.queryForObject(sqlUserId, new MapSqlParameterSource(), Integer.class);
        CommentForm comment = new CommentForm();
        comment.setArticleId(articleId);
        comment.setUserId(userId);
        comment.setContent("コメントテスト");
        return comment;
    }
    
    @Test
    @DisplayName("コメントを挿入する")
    void testInsert() {
        CommentForm comment = createCommentForm();
        Integer commentId = commentRepository.insert(comment);

        assertNotNull(commentId);
        assertTrue(commentId > 0);
    }

    @Test
    @DisplayName("コメントを更新する")
    void testUpdate() {
        CommentForm comment = createCommentForm();
        Integer commentId = commentRepository.insert(comment);

        comment.setContent("Repository Update Test");
        commentRepository.update(comment, commentId, comment.getUserId());

        String sql = "SELECT content FROM comments WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("id", commentId);
        String updatedContent = jdbcTemplate.queryForObject(sql, params, String.class);
        assertEquals(comment.getContent(), updatedContent);
    }

    @Test
    @DisplayName("コメントを削除する")
    void testDelete() {
        CommentForm comment = createCommentForm();
        Integer commentId = commentRepository.insert(comment);

        commentRepository.delete(commentId, comment.getUserId());

        String sql = "SELECT COUNT(*) FROM comments WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("id", commentId);
        Integer count = jdbcTemplate.queryForObject(sql, params, Integer.class);
        assertEquals(0, count);
    }
}
