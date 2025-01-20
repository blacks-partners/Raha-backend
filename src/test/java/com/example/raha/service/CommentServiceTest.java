package com.example.raha.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.example.raha.form.CommentForm;
import com.example.raha.repository.CommentRepository;

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
        comment.setContent("この記事はとても興味深いですね。もっと詳細が知りたいです！");
        return comment;
    }

    @Test
    void testInsert() {
        CommentForm comment = createCommentForm();
        when(commentRepository.insert(comment)).thenReturn(1);

        Integer commentId = commentService.insert(comment);

        assertNotNull(commentId);
        assertEquals(1, commentId);
        verify(commentRepository, times(1)).insert(comment);
    }

    @Test
    void testUpdate() {
        CommentForm comment = createCommentForm();
        comment.setCommentId(2);
        comment.setUserId(1);
        comment.setArticleId(1);
        comment.setContent("内容が分かりやすくまとめられていて、非常に参考になりました");

        commentService.update(comment, comment.getCommentId());

        verify(commentRepository, times(1)).update(comment, comment.getCommentId());
    }

    @Test
    void testDelete() {
        Integer commentId = 1;
        Integer userId = 1;

        doNothing().when(commentRepository).delete(commentId, userId);

        commentService.delete(commentId, userId);

        verify(commentRepository, times(1)).delete(commentId, userId);

    }
}
