package com.example.raha.controller;

import static org.mockito.Mockito.doNothing;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import com.example.raha.domain.User;
import com.example.raha.service.UserService;

@SpringBootTest
@Sql("/TestSql.sql")
@Transactional
public class UserControllerTest {
    @InjectMocks
    private UserController controller;

    @Mock
    private UserService service;

    @Test
    void testDelete() {
        User user = new User(1, "taro", "taro@taro", "password", "hello", LocalDateTime.now(), LocalDateTime.now());
        Integer id = 1;
        doNothing().when(service).delete(id);
    }

    @Test
    void testUpdate() {

    }

    @Test
    void testUserDetails() {

    }
}
