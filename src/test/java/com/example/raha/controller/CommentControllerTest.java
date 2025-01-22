package com.example.raha.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.mockito.Mockito.eq;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.raha.form.CommentForm;
import com.example.raha.service.CommentService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(CommentController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    private CommentService commentService;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @Test
    void testInsertComment() throws Exception {
        Integer commentId = 1;
        doReturn(commentId).when(commentService).insert(any(CommentForm.class));

        Map<String, Object> data = new HashMap<>();
        data.put("articleId", 1);
        data.put("userId", 1);
        data.put("content", "content");

        String requestBody = objectMapper.writeValueAsString(data);

        mockMvc.perform(MockMvcRequestBuilders.post("/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/comments/1"));

        verify(commentService, times(1)).insert(any(CommentForm.class));
    }

    @Test
    void testUpdateComment() throws Exception {
        Integer commentId = 1;
        Integer userId = 1;
        doNothing().when(commentService).update(any(CommentForm.class), eq(commentId), eq(userId));

        Map<String, Object> data = new HashMap<>();
        data.put("content", "change content");

        String requestBody = objectMapper.writeValueAsString(data);

        mockMvc.perform(MockMvcRequestBuilders.put("/comments/{commentId}", commentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isNoContent());

        verify(commentService, times(1)).update(any(CommentForm.class), eq(commentId), eq(Integer.valueOf(userId)));
    }

    @Test
    void testDeleteComment() throws Exception {

    }
}
