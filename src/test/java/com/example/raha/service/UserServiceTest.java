package com.example.raha.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Test
    void testCreateJwtHeader() {

    }

    @Test
    @DisplayName("User情報を削除する処理をテストする。")
    void testDelete() {
        User user = new User(1, "taro", "taro@taro", "password", "hello", LocalDateTime.now(), LocalDateTime.now());
        doNothing().when(repository).delete(user.getUserId());
        service.delete(user.getUserId());
        verify(repository, times(1)).delete(user.getUserId());
    }

    @Test
    @DisplayName("emailからUser情報（パスワードなし）を取り出すテスト。")
    void testFindByEmail() {
        User user = new User(1, "taro", "demo_user@example.com", "password", "hello", LocalDateTime.now(), LocalDateTime.now());
        when(repository.findByEmail("demo_user@example.com")).thenReturn(user);
        User result = service.findByEmail(user.getEmail());
        assertEquals(result, user);
        verify(repository, times(1)).findByEmail("demo_user@example.com");
    }

    @Test
    @DisplayName("emailからUser情報（パスワードなし）を取り出した時nullだった時のテスト。")
    void testNullFindByEmail() {
        doReturn(null).when(repository).findByEmail("demo_user@example.com");
        User result = service.findByEmail("demo_user@example.com");
        assertNull(result);
        verify(repository, times(1)).findByEmail("demo_user@example.com");
    }

    @Test
    @DisplayName("idからUser情報を取り出すテスト。")
    void testLoad() {
        User user = new User(1, "taro", "demo_user@example.com", "password", "hello", LocalDateTime.now(), LocalDateTime.now());
        doReturn(user).when(repository).load(1);
        User result = service.load(1);
        assertEquals(result.getName(), "taro");
        verify(repository, times(1)).load(1);
    }

    @Test
    @DisplayName("emailからUser情報（パスワードあり）を取り出すテスト。")
    void testLoadByEmail() {
        User user = new User(1, "taro", "demo_user@example.com", "password", "hello", LocalDateTime.now(), LocalDateTime.now());
        doReturn(user).when(repository).loadByEmail("demo_user@example.com");
        User result = service.loadByEmail("demo_user@example.com");
        assertEquals(user, result);
        verify(repository, times(1)).loadByEmail("demo_user@example.com");
    }

    @Test
    @DisplayName("emailからUser情報（パスワードあり）を取り出した時nullの時テスト。")
    void testNullLoadByEmail() {
        doReturn(null).when(repository).loadByEmail("demo_user@example.com");
        User result = service.loadByEmail("demo_user@example.com");
        assertNull(result);
        verify(repository, times(1)).loadByEmail("demo_user@example.com");
    }

    @Test
    @DisplayName("User登録ができているか確認するテスト。")
    void testRegister() {
        RegisterUserForm form = new RegisterUserForm("yuki", "b@b", "password");
        Integer id = 1;
        doReturn(id).when(repository).insert(form);
        Integer resultId = service.register(form);
        assertEquals(resultId, id);
        verify(repository, times(1)).insert(form);
    }

    @Test
    @DisplayName("ユーザー更新が行えているかのテスト。")
    void testUpdate() {
        User user = new User(1, "hayato", "demo_user@example.com", "password", "hello", LocalDateTime.now(), LocalDateTime.now());
        UpdateUserForm form = new UpdateUserForm("taro", "taro@taro", "taroです。");
        doNothing().when(repository).update(user.getUserId(), form);
        service.update(user.getUserId(), form);
        verify(repository, times(1)).update(user.getUserId(), form);
    }
}
