package com.example.raha.repository;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.raha.domain.User;

import lombok.RequiredArgsConstructor;

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

    @SuppressWarnings("null")
    private static final RowMapper<User> USER_ROW_MAPPER = (rs, i) -> {
        User user = USER_NOPASS_ROWMAPPER.mapRow(rs, i);
        user.setPassword(rs.getString("password"));
        return user;
    };

    /**
     * ユーザー情報を取得
     * @param id ID
     * @return ユーザー情報
     */
    public User load(Integer id) {
        String sql = "SELECT id,name,email,introduction,created_at,update_at FROM users WHERE id=:id";

        SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);

        return template.queryForObject(sql, param, USER_NOPASS_ROWMAPPER);

    }

    /**
     * メールアドレスからユーザー情報を取得
     * @param email メールアドレス
     * @return ユーザー情報
     */
    public User loadByEmail(String email) {
        String sql = "SELECT id,name,email,password,introduction,created_at,update_at FROM users WHERE email=:email";

        SqlParameterSource param = new MapSqlParameterSource().addValue("email", email);
        try {
            return template.queryForObject(sql, param, USER_ROW_MAPPER);
        } catch (Exception e) {
            return null;
        }
    }
}
