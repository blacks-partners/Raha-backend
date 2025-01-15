package com.example.raha.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.raha.domain.Article;
import com.example.raha.form.ArticleForm;
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
        List<Article> articleList = articleService.findAll();

        return articleList;
    }

    /**
     * 記事詳細情報の取得
     * 
     * @param articleId 記事ID
     * @return article 記事+コメント情報
     */
    @GetMapping("/{articleId}")
    public Article details(@PathVariable Integer articleId) {
        Article article = articleService.articleDetails(articleId);

        return article;
    }

    /**
     * 投稿された記事内容を登録
     * 
     * @param article 登録する記事内容
     * @return articleId 自動採番されたid
     */
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void insert(@RequestBody ArticleForm article) {

        articleService.insert(article);
    }

}
