package com.example.raha.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.example.raha.form.CommentForm;
import com.example.raha.repository.CommentRepository;

/**
 * CommentServiceのテストクラス
 * 
 * @author K.Kawachino
 */
@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
public class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentService commentService;

    private CommentForm createCommentForm() {
        CommentForm comment = new CommentForm();
        comment.setArticleId(1);
        comment.setUserId(1);
        comment.setContent("Comment Test");
        return comment;
    }

    @Test
    @DisplayName("コメントを挿入する")
    void testInsert() {
        CommentForm comment = createCommentForm();
        doReturn(1).when(commentRepository).insert(comment);

        Integer commentId = commentService.insert(comment);

        assertNotNull(commentId);
        assertEquals(1, commentId);
        verify(commentRepository, times(1)).insert(any());
    }

    @Test
    @DisplayName("コメントを更新する")
    void testUpdate() {
        CommentForm comment = createCommentForm();
        comment.setCommentId(1);
        comment.setUserId(1);
        comment.setArticleId(1);
        comment.setContent("Comment Update Test");

        doNothing().when(commentRepository).update(comment, comment.getCommentId(), comment.getUserId());

        commentService.update(comment, comment.getCommentId(), comment.getUserId());

        verify(commentRepository, times(1)).update(any(), any(), any());
    }

    @Test
    @DisplayName("コメントを削除する")
    void testDelete() {
        Integer commentId = 1;
        Integer userId = 1;

        doNothing().when(commentRepository).delete(commentId, userId);

        commentService.delete(commentId, userId);

        verify(commentRepository, times(1)).delete(any(), any());

    }
}
