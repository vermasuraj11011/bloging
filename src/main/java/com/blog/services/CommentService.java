package com.blog.services;

import com.blog.entities.Comment;
import com.blog.payload.CommentDto;

public interface CommentService {
    CommentDto addComment(CommentDto commentDto, Integer postId, Integer userId);

    void deleteComment(Integer commentID);
}
