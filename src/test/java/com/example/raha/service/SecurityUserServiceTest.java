package com.example.raha.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.raha.domain.User;
import com.example.raha.repository.UserRepository;

@SpringBootTest
public class SecurityUserServiceTest {
    
    @InjectMocks
    private SecurityUserService securityUserService;

    @Mock
    private UserRepository userRepository;

    @Test
    public void testLoadUserByUsername() {
        User user = new User();
        user.setUserId(1);
        user.setName("test");
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setIntroduction("test introduction");
        user.setCreatedAt(null);
        user.setUpdatedAt(null);

        String email = "test@example.com";

        when(userRepository.loadByEmail(email)).thenReturn(user);

        UserDetails userDetails = securityUserService.loadUserByUsername(email);
        
        assertEquals(user.getEmail(), userDetails.getUsername());
    }

    @Test
    public void testLoadUserByUsername_NotFound() {
        String email = "null@example.com";

        when(userRepository.loadByEmail(email)).thenReturn(null);

        assertNull(securityUserService.loadUserByUsername(email));
    }
}
