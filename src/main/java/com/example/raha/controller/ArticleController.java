package com.example.raha.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.raha.domain.Article;
import com.example.raha.service.ArticleService;

import lombok.RequiredArgsConstructor;

/**
 * 記事に関するコントローラークラス
 * 
 * @author 金丸天
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;

    /**
     * 記事一覧情報の取得
     * 
     * @return articleList 記事リスト
     */
    @GetMapping("")
    public List<Article> findAllArticles() {
        List<Article> articleList = new ArrayList<>();
        articleList = articleService.findAll();

        return articleList;
    }

}
