package com.example.raha.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.Instant;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UserDetails;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.raha.domain.User;
import com.example.raha.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class SecurityUserServiceTest {
    
    @Autowired
    private SecurityUserService securityUserService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        String secret = "test_secret";
        securityUserService = new SecurityUserService(secret, userRepository);
    }

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

    @SuppressWarnings("null")
    @Test
    public void testCreateJwtHeader_checkBearer() {
        Integer userId = 1;
        HttpHeaders headers = securityUserService.createJwtHeader(userId);

        // プレフィックスがBearer であることを確認
        assertEquals("Bearer ", headers.get("X-AUTH-TOKEN").get(0).substring(0, 7));
    }

    @SuppressWarnings("null")
    @Test
    public void testCreateJwtHeader_checkToken() {
        Integer userId = 1;
        HttpHeaders headers = securityUserService.createJwtHeader(userId);

        // トークンが正しく生成されていることを確認
        String token = headers.get("X-AUTH-TOKEN").get(0).substring(7);
        DecodedJWT decodedJWT = JWT.decode(token);
        assertEquals(1, decodedJWT.getClaim("userId").asInt());
    }

    @SuppressWarnings("null")
    @Test
    public void testCreateJwtHeader_checkExpiration() {
        Integer userId = 1;
        HttpHeaders headers = securityUserService.createJwtHeader(userId);

        // トークンの有効期限が1日であることを確認
        String token = headers.get("X-AUTH-TOKEN").get(0).substring(7);
        DecodedJWT decodedJWT = JWT.decode(token);
        Instant expiresAt = decodedJWT.getExpiresAt().toInstant();
        Instant now = Instant.now();
        Instant expiration = now.plusSeconds(60 * 60 * 24);
        assertTrue(expiresAt.isAfter(now.plusSeconds(60 * 60 * 23)) && expiresAt.isBefore(expiration.plusSeconds(1)));
    }

    @SuppressWarnings("null")
    @Test
    public void testCreateJwtHeader_checkSecret() {
        Integer userId = 1;
        HttpHeaders headers = securityUserService.createJwtHeader(userId);

        // トークンが正しく生成されていることを確認
        String token = headers.get("X-AUTH-TOKEN").get(0).substring(7);
        // シークレットキーが正しいことを確認
        String secret = "test_secret";
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT verifiedJWT = verifier.verify(token);
        assertEquals(1, verifiedJWT.getClaim("userId").asInt());
    }
}
