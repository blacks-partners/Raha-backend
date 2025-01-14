package com.example.raha.repository;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.raha.domain.User;

import lombok.RequiredArgsConstructor;

/**
 * ユーザーに関するリポジトリクラス
 * 
 * @author 金丸天
 */
@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final NamedParameterJdbcTemplate template;

    private static final RowMapper<User> USER_NOPASS_ROWMAPPER = (rs, i) -> {
        User user = new User();
        user.setUserId(rs.getInt("id"));
        user.setName(rs.getString("name"));
        user.setEmail(rs.getString("email"));
        user.setIntroduction(rs.getString("introduction"));
        user.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        user.setCreatedAt(rs.getTimestamp("update_at").toLocalDateTime());
        return user;
    };

    /**
     * ユーザー情報詳細の取得
     * 
     * @return user ユーザー情報
     */
    public User load(Integer id) {
        String sql = "SELECT id,name,email,introduction,created_at,update_at FROM users WHERE id=:id";

        SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);

        return template.queryForObject(sql, param, USER_NOPASS_ROWMAPPER);

    }

}
