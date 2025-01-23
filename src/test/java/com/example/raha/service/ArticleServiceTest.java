package com.example.raha.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.raha.domain.Article;
import com.example.raha.domain.User;
import com.example.raha.form.ArticleForm;
import com.example.raha.repository.ArticleRepository;

@ExtendWith(MockitoExtension.class)
public class ArticleServiceTest {

    @Mock
    private ArticleRepository articleRepository;

    @InjectMocks
    private ArticleService articleService;

    @Test
    @DisplayName("testFindAll()の正常系")
    void testFindAll() {

        LocalDateTime time1 = LocalDateTime.of(2020, 1, 1, 1, 1, 1);
        LocalDateTime time2 = LocalDateTime.of(2021, 1, 1, 1, 1, 1);

        User user = new User(1, "カナマル", "gorousora@icloud.com", null, null, time1, time1);

        Article article1 = new Article(1, "タイトル1", "内容1", user, null, time2, time2);
        Article article2 = new Article(1, "タイトル2", "内容2", user, null, time2, time2);

        List<Article> articleList = new ArrayList<>();
        articleList.add(article1);
        articleList.add(article2);

        doReturn(articleList).when(articleRepository).findAll();

        List<Article> articles = articleService.findAll();

        assertNotNull(articles);
        assertEquals(articleList.get(0).getTitle(), article1.getTitle());
        assertEquals(2, articles.size());
        verify(articleRepository).findAll();

    }

    @Test
    @DisplayName("testArticleDetails()の正常系")
    void testArticleDetails() {

        LocalDateTime time1 = LocalDateTime.of(2020, 1, 1, 1, 1, 1);
        LocalDateTime time2 = LocalDateTime.of(2021, 1, 1, 1, 1, 1);

        User user = new User(1, "カナマル", "gorousora@icloud.com", null, null, time1, time1);

        Article article = new Article(1, "タイトル1", "内容1", user, null, time2, time2);

        Integer articleId = 1;

        doReturn(article).when(articleRepository).articleDetails(articleId);

        Article articleTest = articleService.articleDetails(articleId);

        assertNotNull(articleTest);
        assertEquals("タイトル1", articleTest.getTitle());
        verify(articleRepository).articleDetails(any());

    }

    @Test
    @DisplayName("testInsert()の正常系")
    void testInsert() {
        ArticleForm articleForm = new ArticleForm("タイトル1", "内容1", 1);

        LocalDateTime time1 = LocalDateTime.of(2020, 1, 1, 1, 1, 1);
        LocalDateTime time2 = LocalDateTime.of(2021, 1, 1, 1, 1, 1);

        User user = new User(1, "カナマル", "gorousora@icloud.com", null, null, time1, time1);

        Article article = new Article(1, "タイトル1", "内容1", user, null, time2, time2);

        Integer articleId = 1;

        doReturn(articleId).when(articleRepository).insert(articleForm);

        Integer articleIdTest = articleService.insert(articleForm);

        assertNotNull(articleIdTest);
        assertEquals(1, articleId);
        verify(articleRepository).insert(any());

    }

    @Test
    @DisplayName("testUserArticleFindAll()の正常系")
    void testUserArticleFindAll() {

        Integer userId = 1;

        LocalDateTime time1 = LocalDateTime.of(2020, 1, 1, 1, 1, 1);
        LocalDateTime time2 = LocalDateTime.of(2021, 1, 1, 1, 1, 1);

        User user = new User(userId, "カナマル", "gorousora@icloud.com", null, null, time1, time1);

        Article article1 = new Article(1, "タイトル1", "内容1", user, null, time2, time2);
        Article article2 = new Article(1, "タイトル2", "内容2", user, null, time2, time2);

        List<Article> articleList = new ArrayList<>();
        articleList.add(article1);
        articleList.add(article2);

        doReturn(articleList).when(articleRepository).userArticleFindAll(userId);

        List<Article> articlesListTest = articleService.userArticleFindAll(userId);

        assertNotNull(articlesListTest);
        assertEquals(2, articlesListTest.size());
        assertEquals(articlesListTest.get(0).getTitle(), article1.getTitle());
        verify(articleRepository).userArticleFindAll(any());

    }

    @Test
    @DisplayName("testUpdate()の正常系")
    void testUpdate() {
        Integer articleId = 1;
        Integer userId = 1;
        ArticleForm articleForm = new ArticleForm("タイトル1", "内容1", 1);

        doNothing().when(articleRepository).update(articleForm, articleId, userId);

        articleService.update(articleForm, articleId, userId);

        verify(articleRepository).update(any(), any(), any());
    }

    @Test
    @DisplayName("testDelete()の正常系")
    void testDelete() {

        Integer articleId = 1;
        Integer userId = 1;

        doNothing().when(articleRepository).delete(articleId, userId);

        articleService.delete(articleId, userId);

        verify(articleRepository).delete(any(), any());
    }
}
