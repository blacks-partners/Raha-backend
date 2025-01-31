package com.example.raha.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.raha.domain.User;
import com.example.raha.dto.ArticleDto;
import com.example.raha.service.ArticleService;
import com.example.raha.service.UserService;

import lombok.RequiredArgsConstructor;

import com.example.raha.form.UpdateUserForm;

/**
 * ユーザーに関するコントローラークラス
 *
 * @author S.Kanamaru
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ArticleService articleService;
    private final UserService service;

    /**
     * ユーザー情報詳細の取得
     *
     * @param userId ユーザーID
     * @return user ユーザー情報
     */
    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
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
    public List<ArticleDto> userArticleFindAll(@PathVariable Integer userId) {
        List<ArticleDto> articleList = articleService.userArticleFindAll(userId);
        return articleList;
    }

    /**
     * ユーザーの削除。
     *
     * @param userId ユーザーID
     */
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer userId) {
        service.delete(userId);
    }

    /*
     * ユーザー情報の更新。
     * 
     * @param userId ユーザーID
     * 
     * @param form ユーザー更新フォームの内容。
     */
    @PutMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer userId, @RequestBody UpdateUserForm form) {
        service.update(userId, form);
    }

}
