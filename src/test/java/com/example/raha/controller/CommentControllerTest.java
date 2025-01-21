package com.example.raha.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.raha.form.CommentForm;
import com.example.raha.service.CommentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;

@WebMvcTest(CommentController.class)
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private CommentService commentService;

    @InjectMocks
    private CommentController commentController;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(commentController).build();
        SecurityContextHolder.setContext(securityContext);
    }

    private CommentForm createCommentForm() {
        CommentForm comment = new CommentForm();
        comment.setArticleId(10);
        comment.setUserId(10);
        comment.setContent("コメントテスト");
        return comment;
    }

    @Test
    void testInsertComment() {
        CommentForm comment = createCommentForm();
        Integer commentId = 1;

        when(commentService.insert(any(CommentForm.class))).thenReturn(commentId);

        try {
            mockMvc.perform(MockMvcRequestBuilders.post("/comments")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(comment)))
                    .andExpect(status().isCreated());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(commentService, times(1)).insert(any(CommentForm.class));
    }

    @Test
    void testUpdateComment() throws Exception {
        Integer commentId = 1;
        String userId = "1";

        CommentForm commentForm = new CommentForm();
        commentForm.setArticleId(1);
        commentForm.setUserId(1);
        commentForm.setContent("Updated comment");

        when(securityContext.getAuthentication()).thenReturn(authentication);
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(userId, "password", new ArrayList<>());
        when(authentication.getPrincipal()).thenReturn(userDetails);
        doNothing().when(commentService).update(any(CommentForm.class), eq(commentId), eq(Integer.valueOf(userId)));

        mockMvc.perform(MockMvcRequestBuilders.put("/comments/{commentId}", commentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(commentForm)))
                .andExpect(status().isNoContent());

        verify(commentService, times(1)).update(any(CommentForm.class), eq(commentId), eq(Integer.valueOf(userId)));
        

    }

    @Test
    void testDeleteComment() {
        
    }
}
