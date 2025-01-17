package com.example.raha.repository;

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

import lombok.RequiredArgsConstructor;

/**
 * ユーザーのRepositoryクラス。
 *
 * @author R.Naka
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

    /**
     * ユーザー情報の取得。
     *
     * @param id
     * @return User
     */
    public User load(Integer id) {
        String sql = "SELECT id,name,email,introduction,created_at,updated_at FROM users WHERE id=:id";
        SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
        User user = template.queryForObject(sql, param, USER_NOPASS_ROWMAPPER);
        return user;
    }

    /**
     * ユーザー登録処理。
     *
     * @param user
     * @return ユーザーID
     */
    @SuppressWarnings("null")
    public Integer insert(RegisterUserForm form) {
        String sql = "INSERT INTO users(name,email,password) VALUES(:name, :email, :password);";
        SqlParameterSource param = new BeanPropertySqlParameterSource(form);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        String[] keyColumnName = { "id" };
        template.update(sql, param, keyHolder, keyColumnName);
        Integer id = keyHolder.getKey().intValue();
        return id;
    }

    /**
     * メールアドレスが存在するかの確認。
     *
     * @param email
     * @return Userかnull。
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
     * ユーザーの削除
     * 
     * @param userId ユーザーID
     */
    public void delete(Integer userId) {
        String sql = "DELETE FROM users WHERE id = :userId;";
        SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId);
        template.update(sql, param);
    }

}
