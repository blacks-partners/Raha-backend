package com.example.raha.repository;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.raha.domain.Comment;
import com.example.raha.form.CommentForm;

import lombok.RequiredArgsConstructor;

/**
 * コメントに関するリポジトリクラス
 * 
 * @author K.Kawachino
 */
@Repository
@RequiredArgsConstructor
public class CommentRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final RowMapper<Comment> COMMENT_ROWMAPPER = (rs, i) -> {
        Comment comment = new Comment();
        comment.setCommentId(rs.getInt("id"));
        comment.setContent(rs.getString("content"));
        comment.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        comment.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        return comment;
    };

    /**
     * コメント情報追加
     * 
     * @param comment コメント情報
     * @return id コメントID（単体テストで用いるため）
     */
    public Integer insert(CommentForm comment) {
        String sql = "INSERT INTO comments (content, user_id, article_id) VALUES (:content, :userId, :articleId)";
        SqlParameterSource param = new BeanPropertySqlParameterSource(comment);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String[] keyColumnName = { "id" };
        jdbcTemplate.update(sql, param, keyHolder, keyColumnName);
        Integer id = (keyHolder.getKey().intValue());
        return id;
    }

    /**
     * コメント情報削除
     * 
     * @param commentId コメントID
     */
    public void delete(Integer commentId) {
        String sql = "DELETE FROM comments WHERE id = :id";
        SqlParameterSource param = new MapSqlParameterSource().addValue("id", commentId);
        jdbcTemplate.update(sql, param);
    }
}
