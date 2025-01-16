package com.example.raha.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
    public ResponseEntity<Void> insert(@RequestBody ArticleForm article) {

        Integer articleId = articleService.insert(article);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(articleId)
                .toUri();

        return ResponseEntity.created(location).build();

    }

    /**
     * 記事内容の更新
     * 
     * @param article   更新する気情報
     * @param articleId 対象の記事ID
     */
    @PutMapping("/{articleId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody ArticleForm article, @PathVariable Integer articleId) {
        articleService.update(article, articleId);

    }

}
