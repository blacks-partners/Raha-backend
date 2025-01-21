package com.example.raha.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties.Authentication;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.raha.domain.User;
import com.example.raha.form.CommentForm;
import com.example.raha.service.CommentService;

@ExtendWith(MockitoExtension.class)
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

    @Test
    void testInsertComment() {

    }

    @Test
    void testUpdateComment() {
        Integer commentId = 1;
        String userId = "1";

        CommentForm commentForm = new CommentForm();
        commentForm.setArticleId(1);
        commentForm.setUserId(1);
        commentForm.setContent("Updated comment");

        User user = new User();
        

    }

    @Test
    void testDeleteComment() {
        
    }
}
