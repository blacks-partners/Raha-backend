package com.example.raha.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.util.List;

import com.example.raha.domain.Article;
import com.example.raha.form.ArticleForm;

@SpringBootTest
@Transactional
@Sql("TestSql.sql")
@Import(ArticleRepository.class)
public class ArticleRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final static RowMapper<Integer> MAX_ROW_MAPPER = (rs, i) -> {
        return rs.getInt("max");
    };

    private final static RowMapper<Integer> USERID_ROW_MAPPER = (rs, i) -> {
        return rs.getInt("id");
    };

    @Test
    @DisplayName("findAll()正常系")
    void testFindAll() {
        List<Article> articleListTest = articleRepository.findAll();
        Article firstArticle = articleListTest.get(0);
        Article lastArticle = articleListTest.get(articleListTest.size() - 1);
        assertEquals(2, articleListTest.size());
        assertEquals(firstArticle.getTitle(), "HTMLとCSSで作るWebサイト");
        assertEquals(lastArticle.getTitle(), "Pythonで始めるデータ分析");
    }

    @Test
    @DisplayName("articleDetails()正常系")
    void testArticleDetails() {
        String sql = "SELECT max(id) FROM articles";

        Integer maxId = jdbcTemplate.queryForObject(sql, MAX_ROW_MAPPER);

        Article articleTest = articleRepository.articleDetails(maxId);
        assertThat(articleTest.getTitle()).isEqualTo("HTMLとCSSで作るWebサイト");

    }

    @Test
    @DisplayName("articleDetails()にて該当の記事がない場合")
    void testArticleDetailsNothing() {

        Article nullArticle = articleRepository.articleDetails(10000);
        assertNull(nullArticle);

    }

    @Test
    @DisplayName("testInsert()正常系")
    void testInsert() {
        String userIdSql = "SELECT id FROM users";

        Integer userId = jdbcTemplate.queryForObject(userIdSql, USERID_ROW_MAPPER);

        ArticleForm articleForm = new ArticleForm("デモ記事", "デモ内容", userId);

        Integer insertId = articleRepository.insert(articleForm);

        String sql = "SELECT max(id) FROM articles";

        Integer maxId = jdbcTemplate.queryForObject(sql, MAX_ROW_MAPPER);

        assertThat(insertId).isEqualTo(maxId);

    }

    @Test
    @DisplayName("userArticleFindAll()正常系")
    void testUserArticleFindAll() {

        String userIdSql = "SELECT id FROM users";

        Integer userId = jdbcTemplate.queryForObject(userIdSql, USERID_ROW_MAPPER);
        List<Article> articleListTest = articleRepository.userArticleFindAll(userId);

        Article firstArticle = articleListTest.get(0);
        Article lastArticle = articleListTest.get(articleListTest.size() - 1);
        System.out.println(articleListTest.size() - 1);

        assertEquals(2, articleListTest.size());
        assertEquals(firstArticle.getTitle(), "HTMLとCSSで作るWebサイト");
        assertEquals(lastArticle.getTitle(), "Pythonで始めるデータ分析");

    }

    @Test
    @DisplayName("delete()正常系")
    void testDelete() {
        String maxArticleIdSql = "SELECT max(id) FROM articles";
        Integer articleId = jdbcTemplate.queryForObject(maxArticleIdSql, MAX_ROW_MAPPER);

        String maxUserIdIdSql = "SELECT id FROM users";
        Integer userId = jdbcTemplate.queryForObject(maxUserIdIdSql, USERID_ROW_MAPPER);
        

        articleRepository.delete(articleId, userId);

        Integer maxIdAfterDelete = jdbcTemplate.queryForObject(maxArticleIdSql, MAX_ROW_MAPPER);

        Integer afterArticleId = (articleId != null) ? articleId - 1 : null;

        assertEquals(afterArticleId, maxIdAfterDelete);
    }

    @Test
    @DisplayName("update()正常系")
    void testUpdate() {
        String userIdSql = "SELECT id FROM users";
        Integer userId = jdbcTemplate.queryForObject(userIdSql, USERID_ROW_MAPPER);

        ArticleForm article = new ArticleForm("タイトル1", "内容1", userId);
        Integer articleId = articleRepository.insert(article);

        ArticleForm updateArticle = new ArticleForm("タイトル2", "内容2", userId);
        articleRepository.update(updateArticle, articleId, userId);

        String sql = "SELECT title FROM articles WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("id", articleId);
        String updatedArticle = namedParameterJdbcTemplate.queryForObject(sql, params, String.class);
        assertEquals("タイトル2", updatedArticle);

    }

}
