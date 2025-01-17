package com.example.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;

import com.example.raha.domain.Article;
import com.example.raha.form.ArticleForm;
import com.example.raha.repository.ArticleRepository;
import com.example.raha.service.ArticleService;

@JdbcTest
// @@Import(JdbcTrainingRepository.class)
class ArticleServiceTest {

    @Mock
    private ArticleRepository articleRepository;

    @InjectMocks
    private ArticleService articleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        // Arrange
        List<Article> mockArticles = Arrays.asList(
                new Article(1, "Title 1", "Content 1"),
                new Article(2, "Title 2", "Content 2"));
        when(articleRepository.findAll()).thenReturn(mockArticles);

        // Act
        List<Article> articles = articleService.findAll();

        // Assert
        assertNotNull(articles);
        assertEquals(2, articles.size());
        verify(articleRepository, times(1)).findAll();
    }

    @Test
    void testArticleDetails() {
        // Arrange
        Integer articleId = 1;
        Article mockArticle = new Article(1, "Title", "Content");
        when(articleRepository.articleDetails(articleId)).thenReturn(mockArticle);

        // Act
        Article article = articleService.articleDetails(articleId);

        // Assert
        assertNotNull(article);
        assertEquals("Title", article.getTitle());
        verify(articleRepository, times(1)).articleDetails(articleId);
    }

    @Test
    void testInsert() {
        // Arrange
        ArticleForm articleForm = new ArticleForm("Title", "Content");
        Integer mockArticleId = 1;
        when(articleRepository.insert(articleForm)).thenReturn(mockArticleId);

        // Act
        Integer articleId = articleService.insert(articleForm);

        // Assert
        assertNotNull(articleId);
        assertEquals(1, articleId);
        verify(articleRepository, times(1)).insert(articleForm);
    }

    @Test
    void testUserArticleFindAll() {
        // Arrange
        Integer userId = 1;
        List<Article> mockArticles = Arrays.asList(
                new Article(1, "Title 1", "Content 1"),
                new Article(2, "Title 2", "Content 2"));
        when(articleRepository.userArticleFindAll(userId)).thenReturn(mockArticles);

        // Act
        List<Article> articles = articleService.userArticleFindAll(userId);

        // Assert
        assertNotNull(articles);
        assertEquals(2, articles.size());
        verify(articleRepository, times(1)).userArticleFindAll(userId);
    }

    @Test
    void testUpdate() {
        // Arrange
        ArticleForm articleForm = new ArticleForm("Updated Title", "Updated Content");
        Integer articleId = 1;

        // Act
        articleService.update(articleForm, articleId);

        // Assert
        verify(articleRepository, times(1)).update(articleForm, articleId);
    }

    @Test
    void testDelete() {
        // Arrange
        Integer articleId = 1;

        // Act
        articleService.delete(articleId);

        // Assert
        verify(articleRepository, times(1)).delete(articleId);
    }
}
