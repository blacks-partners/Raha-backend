package com.example.raha.repository;

import java.time.LocalDateTime;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.raha.domain.User;
import com.example.raha.form.RegisterUserForm;
import com.example.raha.form.UpdateUserForm;

import lombok.RequiredArgsConstructor;

/**
 * ユーザーに関するリポジトリクラス
 * 
 * @author T.Kanamaru
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
        user.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());

        return user;
    };

    @SuppressWarnings("null")
    private static final RowMapper<User> USER_ROW_MAPPER = (rs, i) -> {
        User user = USER_NOPASS_ROWMAPPER.mapRow(rs, i);
        user.setPassword(rs.getString("password"));
        return user;
    };

    /**
     * ユーザー情報詳細の取得
     * 
     * @param id ユーザーID
     * @return User ユーザー
     */
    public User load(Integer id) {
        String sql = "SELECT id,name,email,introduction,created_at,updated_at FROM users WHERE id=:id";
        SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
        try {
            User user = template.queryForObject(sql, param, USER_NOPASS_ROWMAPPER);
            return user;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /**
     * ユーザー登録処理。
     *
     * @param user ユーザー
     * @return userId ユーザーID
     */
    @SuppressWarnings("null")
    public Integer insert(RegisterUserForm form) {
        String sql = "INSERT INTO users(name,email,password) VALUES(:name, :email, :password);";
        SqlParameterSource param = new BeanPropertySqlParameterSource(form);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        String[] keyColumnName = { "id" };
        template.update(sql, param, keyHolder, keyColumnName);
        Integer userId = keyHolder.getKey().intValue();
        return userId;
    }

    /**
     * メールアドレスが存在するかの確認。
     *
     * @param email メールアドレス
     * @return User ユーザー
     */
    public User findByEmail(String email) {
        String sql = "SELECT id,name,email,introduction,created_at,updated_at FROM users WHERE email = :email;";
        SqlParameterSource param = new MapSqlParameterSource().addValue("email", email);
        try {
            User user = template.queryForObject(sql, param, USER_NOPASS_ROWMAPPER);
            return user;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /**
     * メールアドレスからユーザー情報を取得
     * 
     * @param email メールアドレス
     * @return user ユーザー情報
     */
    public User loadByEmail(String email) {
        String sql = "SELECT id,name,email,password,introduction,created_at,updated_at FROM users WHERE email=:email";

        SqlParameterSource param = new MapSqlParameterSource().addValue("email", email);
        try {
            return template.queryForObject(sql, param, USER_ROW_MAPPER);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    /**
     * ユーザーの削除
     * 
     * @param userId ユーザーID
     */
    public void delete(Integer userId) {
        String sql = "DELETE FROM users WHERE id = :userId;";
        SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId);
        template.update(sql, param);
    }

    /*
     * ユーザー情報の更新。
     * 
     * @param userId ユーザーID
     * @param form ユーザー更新フォームの内容。
     */
    public void update(Integer userId, UpdateUserForm form) {
        String sql = "UPDATE users SET name = :name, email = :email, introduction = :introduction, updated_at = :updated_at WHERE id = :userId;";
        LocalDateTime updateTime = LocalDateTime.now();
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("name", form.getName())
                .addValue("email", form.getEmail())
                .addValue("introduction", form.getIntroduction())
                .addValue("updated_at", updateTime)
                .addValue("userId", userId);
        template.update(sql, param);
    }

}
