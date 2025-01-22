package com.example.raha.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
@Sql("/testData.sql")
@Transactional
public class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    @Test
    @DisplayName("User情報が削除できているか確認するテスト。")
    void testDelete() {
        Integer userId = 1;
        repository.delete(userId);
        User result = repository.load(userId);
        assertNull(result);
    }

    @Test
    @DisplayName("emailからUser（パスワード）が取り出せているかのテスト。")
    void testFindByEmail() {
        String email = "demo_user@example.com";
        Integer id = 1;
        User user = repository.findByEmail(email);
        User result = repository.load(id);
        if(user == null) {
            assertNull(result);
        } else {
            assertEquals(user, result);
        }
    }

    @Test
    @DisplayName("UserFormの情報から登録処理ができているかのテスト。")
    void testInsert() {
        RegisterUserForm form = new RegisterUserForm("taro", "taro@taro", "taro");
        Integer id = 2;
        repository.insert(form);
        User result = repository.load(id);
        assertEquals(result.getName(), "taro");
    }

    @Test
    @DisplayName("idのUserが呼び出せているかのテスト。")
    void testLoad() {
        User result = repository.load(1);
        if(result==null){
            assertNull(result);
        } else {
            assertEquals(result.getEmail(), "demo_user@example.com");
        }
    }

    @Test
    @DisplayName("emailからUser（パスワードあり）が取得できているかのテスト。")
    void testLoadByEmail() {
        User result = repository.findByEmail("demo_user@example.com");
        User user = repository.load(1);
        assertEquals(result.getName(), user.getName());
    }

    @Test
    @DisplayName("User情報の更新処理が実行できているかのテスト。")
    void testUpdate() {
        UpdateUserForm form = new UpdateUserForm("taro", "taro@taro", "taroです。");
        Integer id = 1;
        repository.update(id, form);
        User result = repository.load(id);
        assertEquals(result.getName(), "taro");
    }
}
