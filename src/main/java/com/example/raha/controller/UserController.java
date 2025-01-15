package com.example.raha.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.raha.domain.User;
import com.example.raha.service.UserService;

import lombok.RequiredArgsConstructor;

/**
 * ユーザーに関するコントローラークラス
 * 
 * @author 金丸天
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    /**
     * ユーザー情報詳細の取得
     * 
     * @return user ユーザー情報
     */
    @GetMapping("/{userId}")
    public User userDetails(@PathVariable Integer userId) {
        User user = service.load(userId);
        return user;

    }
}
