package com.example.raha.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.raha.domain.User;
import com.example.raha.error.GlobalExceptionHandler;
import com.example.raha.error.invalidAuthenticationException;
import com.example.raha.form.CheckEmailForm;
import com.example.raha.form.LoginForm;
import com.example.raha.service.SecurityUserService;
import com.example.raha.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
@DisplayName("AccountControllerのテスト")
public class AccountControllerTest {
    private MockMvc mockMvc;

    @Mock
    private DaoAuthenticationProvider provider;

    @Mock
    private UserService userService;

    @Mock
    private SecurityUserService securityUserService;

    @InjectMocks
    private AccountController accountController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(accountController)
                .setControllerAdvice(new GlobalExceptionHandler()).build();
    }

    @Test
    @DisplayName("ログイン成功時のテスト")
    public void testLogin() throws Exception {
        User user = new User();
        user.setUserId(1);
        user.setEmail("test@example.com");
        user.setPassword("$2a$08$n5ghv7TGowSllmO0hwacyeJLEXgNOZ368/xTp4YFW/5FZznV3I23i");

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-AUTH-TOKEN", "Bearer test_token");

        LoginForm loginForm = new LoginForm();
        loginForm.setEmail("test@example.com");
        loginForm.setPassword("test");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(loginForm);

        when(userService.loadByEmail(loginForm.getEmail())).thenReturn(user);
        when(securityUserService.createJwtHeader(user.getUserId())).thenReturn(headers);

        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        verify(userService, times(1)).loadByEmail(user.getEmail());
        verify(securityUserService, times(1)).createJwtHeader(user.getUserId());
    }

    @Test
    @DisplayName("ログイン失敗時のテスト")
    public void testLogin_failed() throws Exception {
        LoginForm loginForm = new LoginForm();
        loginForm.setEmail("nonexist@example.com");
        loginForm.setPassword("wrong_password");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(loginForm);

        when(provider.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new invalidAuthenticationException("test exception"));

        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("メールアドレス又はパスワードが誤っています"));
        verify(provider, times(1)).authenticate(any());
    }

    @Test
    @DisplayName("メール重複がない場合のテスト")
    public void testCheckEmail() throws Exception {
        CheckEmailForm checkEmailForm = new CheckEmailForm();
        checkEmailForm.setEmail("test@gmail.com");

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(checkEmailForm);

        when(userService.findByEmail(checkEmailForm.getEmail())).thenReturn(null);

        mockMvc.perform(get("/check-email")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
        verify(userService).findByEmail(checkEmailForm.getEmail());
    }

    @Test
    @DisplayName("メール重複がある場合のテスト")
    public void testCheckEmail_conflict() throws Exception {
        CheckEmailForm checkEmailForm = new CheckEmailForm();
        checkEmailForm.setEmail("demo_user@example.com");

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(checkEmailForm);

        when(userService.findByEmail(checkEmailForm.getEmail())).thenReturn(new User());

        mockMvc.perform(get("/check-email")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("入力されたメールアドレスは既に登録されています"));

        verify(userService).findByEmail(checkEmailForm.getEmail());
    }
}