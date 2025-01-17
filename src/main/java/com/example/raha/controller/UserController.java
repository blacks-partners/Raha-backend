package com.example.raha.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.raha.domain.Article;
import com.example.raha.domain.User;
import com.example.raha.service.ArticleService;
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

    private final UserService userService;
    private final ArticleService articleService;

    /**
     * ユーザー情報詳細の取得
     * 
     * @return user ユーザー情報
     */
    @GetMapping("/{userId}")
    public User userDetails(@PathVariable Integer userId) {
        User user = userService.load(userId);
        return user;
    }

    /**
     * ユーザーの記事一覧を取得。
     * 
     * @param userId
     * @return ユーザーの記事一覧。
     */
    @GetMapping("/{userId}/articles")
    public List<Article> userArticleFindAll(@PathVariable Integer userId) {
        List<Article> articleList = articleService.userArticleFindAll(userId);
        return articleList;
    }
}
