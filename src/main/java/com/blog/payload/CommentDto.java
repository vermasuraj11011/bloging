package com.blog.payload;

import com.blog.entities.User;
import lombok.Data;

@Data
public class CommentDto {
    private Integer id;
    private String content;
//    private User user;
}
