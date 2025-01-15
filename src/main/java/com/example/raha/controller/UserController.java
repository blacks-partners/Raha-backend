package com.example.raha.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.raha.domain.User;
import com.example.raha.service.UserService;

import lombok.RequiredArgsConstructor;

/**
 * Userのコントローラー
 * @author nakaryunosuke
 */

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    /**
     * User情報を取得する。
     * @param userId
     * @return user
     */
    @GetMapping("/{userId}")
    public User userDetails(@PathVariable Integer userId) {
        User user = service.load(userId);
        return user;
    }

}
