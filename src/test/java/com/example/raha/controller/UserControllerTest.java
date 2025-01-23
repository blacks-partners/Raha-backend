package com.example.raha.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.example.raha.domain.User;
import com.example.raha.service.UserService;

/**
 * UserControllerクラスのテスト。
 * 
 * @author R.Naka
 */
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@Transactional
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService service;

    @Test
    @DisplayName("Userの退会処理を確認するテスト。")
    void testDelete() throws Exception{
        User user = new User(1, "taro", "taro@taro", "password", "hello", LocalDateTime.now(), LocalDateTime.now());
        Integer userId = user.getUserId();
        Mockito.when(service.load(userId)).thenReturn(user);

        mockMvc.perform(
                delete("/users/{userId}", 1)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());
    }

    @Test
    void testUpdate() {

    }

    @Test
    @DisplayName("User詳細処理を確認するテスト。")
    void testUserDetails() throws Exception {
        User user = new User(1, "taro", "taro@taro", "password", "hello", LocalDateTime.now(), LocalDateTime.now());
        Integer userId = user.getUserId();
        Mockito.when(service.load(userId)).thenReturn(user);

        mockMvc.perform(
                get("/users/{userId}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(7))
                .andExpect(jsonPath("$.name").value("taro"))

                ;

    }
}
