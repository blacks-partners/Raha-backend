package com.example.raha.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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
    @DisplayName("コメントを挿入する")
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
    @DisplayName("コメントを更新する（userId = 1）")
    void testUpdateComment() throws Exception {
        Integer commentId = 1;
        Integer userId = 1;

        doNothing().when(commentService).update(any(CommentForm.class), eq(commentId), eq(userId));

        Map<String, Object> data = new HashMap<>();
        data.put("content", "change content");

        String requestBody = objectMapper.writeValueAsString(data);

        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(userId);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        mockMvc.perform(MockMvcRequestBuilders.put("/comments/{commentId}", commentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isNoContent());

        verify(commentService, times(1)).update(any(CommentForm.class), eq(commentId), eq(userId));
    }

    @Test
    @DisplayName("コメントを更新する(userId = null)")
    void testUpdateComment_NullUserId() throws Exception {
        Integer commentId = 1;
        Integer userId = null;

        doNothing().when(commentService).update(any(CommentForm.class), eq(commentId), eq(userId));

        Map<String, Object> data = new HashMap<>();
        data.put("content", "change content");

        String requestBody = objectMapper.writeValueAsString(data);

        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(userId);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        mockMvc.perform(MockMvcRequestBuilders.put("/comments/{commentId}", commentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isNoContent());

        verify(commentService, never()).update(any(CommentForm.class), eq(commentId), eq(userId));
    }

    @Test
    @DisplayName("コメントを削除する（userId = 1）")
    void testDeleteComment() throws Exception {
        Integer commentId = 1;
        Integer userId = 1;

        doNothing().when(commentService).delete(eq(commentId), eq(userId));

        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(userId);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        mockMvc.perform(MockMvcRequestBuilders.delete("/comments/{commentId}", commentId))
                .andExpect(status().isNoContent());

        verify(commentService, times(1)).delete(eq(commentId), eq(userId));
    }

    @Test
    @DisplayName("コメントを削除する(userId = null)")
    void testDeleteComment_NullUserId() throws Exception {
        Integer commentId = 1;
        Integer userId = null;

        doNothing().when(commentService).delete(eq(commentId), eq(userId));

        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(userId);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        mockMvc.perform(MockMvcRequestBuilders.delete("/comments/{commentId}", commentId))
                .andExpect(status().isNoContent());

        verify(commentService, never()).delete(eq(commentId), eq(userId));
    }
}
