package com.example.raha.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.raha.domain.Article;
import com.example.raha.form.ArticleForm;
import com.example.raha.repository.ArticleRepository;

import lombok.RequiredArgsConstructor;

/**
 * 記事に関するサービスクラス
 * 
 * @author 金丸天
 */
@Service
@Transactional
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    /**
     * 記事一覧情報の取得
     * 
     * @return articleList 記事リスト
     */
    public List<Article> findAll() {
        List<Article> articleList = articleRepository.findAll();
        return articleList;
    }

    /**
     * 記事詳細情報の取得
     * 
     * @param articleId 記事ID
     * @return article 記事+コメント情報
     */
    public Article articleDetails(Integer articleId) {
        Article article = articleRepository.articleDetails(articleId);

        return article;
    }

    /**
     * 投稿された記事内容を登録
     * 
     * @param article 登録する記事内容
     * @return articleId 自動採番されたid
     */
    public Integer insert(ArticleForm article) {
        Integer articleId = articleRepository.insert(article);

        return articleId;
    }

    /**
     * ユーザーの記事一覧を検索する。
     * @param userId
     * @return　ユーザーの記事一覧。
     */
    public List<Article> userArticleFindAll(Integer userId) {
        List<Article> articleList = articleRepository.userArticleFindAll(userId);
        return articleList;
    }
    /* 記事内容の更新
     * 
     * @param article   更新する気情報
     * @param articleId 対象の記事ID
     */
    public void update(ArticleForm article, Integer articleId) {
        articleRepository.update(article, articleId);

    }

    /**
     * 該当の記事削除
     * 
     * @param articleId 記事ID
     */
    public void delete(Integer articleId) {
        articleRepository.delete(articleId);
    }

}
