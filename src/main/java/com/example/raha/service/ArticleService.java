package com.example.raha.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.raha.domain.Article;
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

}
