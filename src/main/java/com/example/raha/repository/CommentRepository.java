package com.example.raha.repository;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.raha.domain.Comment;

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
        comment.setCommentId(rs.getInt("c_id"));
        comment.setContent(rs.getString("c_content"));
        comment.setCreatedAt(rs.getTimestamp("c_created_at").toLocalDateTime());
        comment.setUpdatedAt(rs.getTimestamp("c_update_at").toLocalDateTime());
        return comment;
    };

    /**
     * コメント情報追加
     * 
     * @param comment
     */
    public void insert(Comment comment) {
        String sql = "INSERT INTO comments (c_content, c_created_at, c_update_at) VALUES (:content, :createdAt, :updatedAt)";
        SqlParameterSource param = new BeanPropertySqlParameterSource(comment);
        jdbcTemplate.update(sql, param);
    }
}
