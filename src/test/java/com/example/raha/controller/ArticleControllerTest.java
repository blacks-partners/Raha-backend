package com.example.raha.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
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

        @MockitoBean
        private ArticleService articleService;

        @Test
        @DisplayName("testFindAllArticles()の正常系")
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
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$[0].title").value("タイトル1"));
        }

        @Test
        @DisplayName("testDetails()の正常系")
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
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.title").value("タイトル1"));

        }

        @Test
        @DisplayName("testInsert()の正常系")
        void testInsert() throws Exception {
                Integer insertArticleId = 10;

                ArticleForm articleForm = new ArticleForm("タイトル1", "内容2", 1);

                doReturn(insertArticleId).when(articleService).insert(any(ArticleForm.class));

                Map<String, Object> articleIdPassMap = new HashMap<>();
                articleIdPassMap.put("title", "タイトル1");
                articleIdPassMap.put("content", "内容");
                articleIdPassMap.put("userId", 1);

                String requestBody = objectMapper.writeValueAsString(articleIdPassMap);

                mockMvc.perform(
                                post("/articles")
                                                .content(requestBody)
                                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isCreated())
                                .andExpect(header().string("Location", "http://localhost/articles/10"))
                                .andExpect(jsonPath("$.articleId").value("10"));

        }

        @Test
        @DisplayName("testDelete()の正常系")
        void testDelete() throws Exception {
                int articleId = 1;
                int userId = 1;
                doNothing().when(articleService).delete(any(), any());

                Authentication authentication = mock(Authentication.class);
                when(authentication.getPrincipal()).thenReturn(userId);
                SecurityContextHolder.getContext().setAuthentication(authentication);

                mockMvc.perform(
                                delete("/articles/{articleId}", articleId)
                                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isNoContent());
        }

        @Test
        @DisplayName("testDelete()にてjwtから取得したuserIdがnullであるケース")
        void testDeleteUserNull() throws Exception {
                Integer articleId = 1;
                Integer userId = null;
                doNothing().when(articleService).delete(any(), any());

                Authentication authentication = mock(Authentication.class);
                when(authentication.getPrincipal()).thenReturn(userId);
                SecurityContextHolder.getContext().setAuthentication(authentication);

                mockMvc.perform(
                                delete("/articles/{articleId}", articleId)
                                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isNoContent());
        }

        @Test
        @DisplayName("testUpdate()の正常系")
        void testUpdate() throws Exception {
                Integer articleId = 1;
                Integer userId = 1;

                ArticleForm articleForm = new ArticleForm("タイトル1", "内容1", 1);

                doNothing().when(articleService).update(articleForm, articleId, userId);

                Map<String, Object> articleIdPassMap = new HashMap<>();
                articleIdPassMap.put("title", "タイトル1");
                articleIdPassMap.put("content", "内容");

                String requestBody = objectMapper.writeValueAsString(articleIdPassMap);

                Authentication authentication = mock(Authentication.class);
                when(authentication.getPrincipal()).thenReturn(userId);
                SecurityContextHolder.getContext().setAuthentication(authentication);

                mockMvc.perform(
                                put("/articles/{articleId}", articleId)
                                                .content(requestBody)
                                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isNoContent());

                verify(articleService).update(any(), any(), any());
        }

        @Test
        @DisplayName("testUpdate()にてarticleIdがnullであるケース")
        void testUpdateUserNull() throws Exception {
                Integer articleId = 1;
                Integer userId = null;

                ArticleForm articleForm = new ArticleForm("タイトル1", "内容1", 1);

                doNothing().when(articleService).update(articleForm, articleId, userId);

                Map<String, Object> articleIdPassMap = new HashMap<>();
                articleIdPassMap.put("title", "タイトル1");
                articleIdPassMap.put("content", "内容");

                String requestBody = objectMapper.writeValueAsString(articleIdPassMap);

                Authentication authentication = mock(Authentication.class);
                when(authentication.getPrincipal()).thenReturn(userId);
                SecurityContextHolder.getContext().setAuthentication(authentication);

                mockMvc.perform(
                                put("/articles/{articleId}", articleId)
                                                .content(requestBody)
                                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isNoContent());

                verify(articleService, never()).update(any(), any(), any());
        }
}
