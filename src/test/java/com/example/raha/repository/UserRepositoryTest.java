package com.example.raha.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import com.example.raha.domain.User;
import com.example.raha.form.RegisterUserForm;
import com.example.raha.form.UpdateUserForm;

/**
 * UserRepositoryのテスト。
 * @author R.naka
 */

@SpringBootTest
@Sql("/TestSql.sql")
@Transactional
public class UserRepositoryTest {

    @Autowired
    private UserRepository repository;
    @Autowired
    private NamedParameterJdbcTemplate template;

    private static final RowMapper<User> USER_ROW_MAPPER = (rs, i) -> {
        User user = new User();
        user.setUserId(rs.getInt("id"));
        user.setName(rs.getString("name"));
        user.setEmail(rs.getString("email"));
        user.setIntroduction(rs.getString("introduction"));
        user.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        user.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        return user;
    };

    private static final RowMapper<Integer> MAX_USERID_ROWMAPPER = (rs, i) -> {
        Integer userId = rs.getInt("id");
        return userId;
    };

    @Test
    @DisplayName("User情報が削除できているか確認するテスト。")
    void testDelete() {
        String sql = "SELECT id FROM users ORDER BY id DESC LIMIT 1;";
        SqlParameterSource param = new MapSqlParameterSource();
        Integer maxUserId = template.queryForObject(sql, param,  MAX_USERID_ROWMAPPER);
        repository.delete(maxUserId);
        User result = repository.load(maxUserId);
        assertNull(result);
    }

    @Test
    @DisplayName("emailからUser（パスワードなし）が取り出せているかのテスト。")
    void testFindByEmail() {
        String email = "demo_user4@example.com";
        User result = repository.findByEmail(email);
        assertEquals(result.getEmail(), email);
    }

    @Test
    @DisplayName("emailからUser（パスワードなし）を呼び出したがnullの時のテスト。")
    void testNullFindByEmail() {
        User result = repository.findByEmail(null);
        assertNull(result);
    }

    @Test
    @DisplayName("RegisterUserFormの情報から登録処理ができているかのテスト。")
    void testInsert() {
        RegisterUserForm form = new RegisterUserForm("taro", "taro@taro", "taro");
        Integer userId = repository.insert(form);
        User user = repository.load(userId);
        assertEquals(user.getName(), "taro");
    }

    @Test
    @DisplayName("idのUserが呼び出せているかのテスト。")
    void testLoad() {
        String sql = "SELECT id FROM users ORDER BY id ASC LIMIT 1;";
        SqlParameterSource param = new MapSqlParameterSource();
        Integer userId = template.queryForObject(sql, param, MAX_USERID_ROWMAPPER);
        User result = repository.load(userId);
        assertEquals(result.getEmail(), "demo_user4@example.com");
    }

    @Test
    @DisplayName("idのUserがNullの時のテスト。")
    void testNullLoad() {
        User result = repository.load(null);
        assertNull(result);
    }

    @Test
    @DisplayName("emailからUser（パスワードあり）が取得できているかのテスト。")
    void testLoadByEmail() {
        User result = repository.loadByEmail("demo_user4@example.com");
        String sql = "SELECT id FROM users ORDER BY id DESC LIMIT 1;";
        SqlParameterSource param = new MapSqlParameterSource();
        Integer maxUserId = template.queryForObject(sql, param, MAX_USERID_ROWMAPPER);
        User user = repository.load(maxUserId);
        assertEquals(result.getName(), user.getName());
    }

    @Test
    @DisplayName("loadByEmailがnullの時用のテスト。")
    void testNullLoadByEmail() {
        User user = repository.loadByEmail(null);
        assertNull(user);
    }

    @Test
    @DisplayName("User情報の更新処理が実行できているかのテスト。")
    void testUpdate() {
        UpdateUserForm form = new UpdateUserForm("taro", "taro@taro", "taroです。");
        String sql = "SELECT id FROM users ORDER BY id DESC LIMIT 1";
        SqlParameterSource param = new MapSqlParameterSource();
        Integer userId = template.queryForObject(sql, param, MAX_USERID_ROWMAPPER);

        repository.update(userId, form);
        User user = repository.load(userId);

        assertEquals(user.getName(), "taro");
    }

}
