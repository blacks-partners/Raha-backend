package com.example.raha.controller;

import static org.mockito.Mockito.doReturn;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.raha.domain.Article;
import com.example.raha.domain.User;
import com.example.raha.form.ArticleForm;
import com.example.raha.service.ArticleService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ArticleController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ArticleControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArticleService articleService;

    @Test
    void testFindAllArticles() throws Exception {

        LocalDateTime time1 = LocalDateTime.of(2020, 1, 1, 1, 1, 1);
        LocalDateTime time2 = LocalDateTime.of(2021, 1, 1, 1, 1, 1);

        User user = new User(1, "カナマル", "gorousora@icloud.com", null, null, time1, time1);

        Article article1 = new Article(1, "タイトル1", "内容1", user, null, time2, time2);
        Article article2 = new Article(1, "タイトル2", "内容2", user, null, time2, time2);

        List<Article> articleList = new ArrayList<>();
        articleList.add(article1);
        articleList.add(article2);

        Map<String, Object> data = new HashMap<>();

        String requestBody = objectMapper.writeValueAsString(data);

        doReturn(articleList).when(articleService).findAll();

        mockMvc.perform(
                get("/articles")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testDetails() throws Exception {
        Integer articleId = 10;

        LocalDateTime time1 = LocalDateTime.of(2020, 1, 1, 1, 1, 1);
        LocalDateTime time2 = LocalDateTime.of(2021, 1, 1, 1, 1, 1);

        User user = new User(1, "カナマル", "gorousora@icloud.com", null, null, time1,
                time1);

        Article article = new Article(1, "タイトル1", "内容1", user, null, time2, time2);

        doReturn(article).when(articleService).articleDetails(articleId);
        Map<String, Article> articleIdPassMap = new HashMap<>();
        articleIdPassMap.put("Article", article);

        String requestBody = objectMapper.writeValueAsString(articleIdPassMap);

        mockMvc.perform(
                get("/articles/{articleId}", articleId)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    void testInsert() throws Exception {
        Integer insertArticleId = 10;

        ArticleForm articleForm = new ArticleForm("タイトル1", "内容2", 1);

        doReturn(insertArticleId).when(articleService).insert(articleForm);

        Integer articleId = articleService.insert(articleForm);
        Map<String, Integer> articleIdPassMap = new HashMap<>();
        articleIdPassMap.put("articleId", articleId);

        String requestBody = objectMapper.writeValueAsString(articleIdPassMap);

        mockMvc.perform(
                post("/articles")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost:8080/articles/10"));

    }

    @Test
    void testDelete() {

    }

    @Test
    void testUpdate() {

    }
}
