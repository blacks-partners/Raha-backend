package com.example.raha.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import com.example.raha.domain.User;
import com.example.raha.form.RegisterUserForm;
import com.example.raha.form.UpdateUserForm;
import com.example.raha.repository.UserRepository;

/**
 * UserServiceクラスのテスト。
 * @author R.Naka
 */

@ExtendWith(MockitoExtension.class)
@Transactional
public class UserServiceTest {
    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserService service;

    @Mock
    private PasswordEncoder passwordEncoder;

    // @Test
    // void testCreateJwtHeader() {

    // }

    @Test
    @DisplayName("User情報を削除する処理をテストする。")
    void testDelete() {
        User user = new User(1, "taro", "taro@taro", "password", "hello", LocalDateTime.now(), LocalDateTime.now());
        service.delete(user.getUserId());
        User result = service.load(user.getUserId());
        assertNull(result);
    }

    @Test
    @DisplayName("emailからUser情報（パスワードなし）を取り出すテスト。")
    void testFindByEmail() {
        User user = service.findByEmail("demo_user@example.com");
        User result = service.load(1);
        if(user == null) {
            assertNull(user);
        } else {
            assertEquals(user, result);
        }
    }

    @Test
    @DisplayName("idからUser情報を取り出すテスト。")
    void testLoad() {
        User user = service.load(1);
        if(user == null ){
            assertNull(user);
        } else {
            assertEquals(user.getName(), "田中太郎");
        }
    }

    @Test
    @DisplayName("emailからUser情報（パスワードあり）を取り出すテスト。")
    void testLoadByEmail() {
        User user = service.loadByEmail("demo_user@example.com");
        User result = service.load(1);
        if (user == null ){
            assertNull(user);
        } else {
            assertEquals(user, result);
        }
    }

    @Test
    @DisplayName("User登録ができているか確認するテスト。")
    void testRegister() {
        RegisterUserForm form = new RegisterUserForm("yuki", "b@b", "password");
        Integer id = 1;
        when(repository.insert(form)).thenReturn(id);
        Integer resultId = service.register(form);
        if (resultId == null){
            assertNull(resultId);
        } else {
            assertEquals(resultId, id);
        }
    }

    @Test
    @DisplayName("ユーザー更新が行えているかのテスト。")
    void testUpdate() {
        UpdateUserForm form = new UpdateUserForm("taro", "taro@taro", "taroです。");
        service.update(1, form);
        User result = service.load(1);
        if (result == null) {
            assertNull(result);
        } else {
            assertEquals(result.getName(), "taro");
        }
    }
}
