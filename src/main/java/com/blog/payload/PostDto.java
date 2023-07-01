package com.blog.payload;

import com.blog.entities.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {
    private String id;

    @NotEmpty
    @Size(min = 4, message = "Name should be not empty and minimum of 4 character")
    private String postTitle;

    @NotEmpty
    private String postContent;

    @NotEmpty
    private String imageName;
    private Date addedDate;
    private CategoryDto category;
    private UserDto user;
    private Set<CommentDto> comments = new HashSet<>();
}
