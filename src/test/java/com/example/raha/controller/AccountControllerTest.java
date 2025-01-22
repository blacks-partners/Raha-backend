package com.example.raha.controller;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.test.web.servlet.MockMvc;

import com.example.raha.service.SecurityUserService;

@SpringBootTest
public class AccountControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DaoAuthenticationProvider provider;

    @Mock
    private SecurityUserService securityUserService;

    @InjectMocks
    private AccountController accountController;

    public void testLogin() {
        
    }
    
}
