package com.example.raha.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

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
import com.example.raha.form.UpdateUserForm;
import com.example.raha.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

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

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Userの退会処理を確認するテスト。")
    void testDelete() throws Exception {
        User user = new User(1, "taro", "taro@taro", "password", "hello", LocalDateTime.now(), LocalDateTime.now());
        Integer userId = user.getUserId();
        doNothing().when(service).delete(userId);

        mockMvc.perform(
                delete("/users/{userId}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        verify(service, times(1)).delete(userId);
    }

    @Test
    @DisplayName("User情報の更新処理を確認するメソッド。")
    void testUpdate() throws Exception {
        User user = new User(1, "taro", "taro@taro", "password", "hello", LocalDateTime.now(), LocalDateTime.now());
        Integer userId = user.getUserId();

        UpdateUserForm form = new UpdateUserForm("keta", "keta@keta", "ketaです。");

        doNothing().when(service).update(userId, form);
        Map<String, Object> data = new HashMap<>();
        data.put("name", "keta");
        data.put("email", "keta@keta");
        data.put("introduction", "ketaです。");
        String requestBody = objectMapper.writeValueAsString(data);
        mockMvc.perform(
                put("/users/{userId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        )
                .andExpect(status().isNoContent())
                ;
        verify(service, times(1)).update(userId, form);
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
        verify(service, times(1)).load(userId);
    }
}
