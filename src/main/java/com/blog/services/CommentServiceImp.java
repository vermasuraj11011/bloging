package com.blog.services;

import com.blog.entities.Comment;
import com.blog.entities.Post;
import com.blog.entities.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payload.CommentDto;
import com.blog.payload.PostDto;
import com.blog.payload.UserDto;
import com.blog.repositories.CommentRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImp implements CommentService {

    @Autowired
    private PostService postService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private UserService userService;

    @Override
    public CommentDto addComment(CommentDto commentDto, Integer postId, Integer userId) {
        PostDto postDto = this.postService.getPostById(postId);
        Post post = this.modelMapper.map(postDto, Post.class);
        UserDto userDto = this.userService.getUserById(userId);
        User user = this.modelMapper.map(userDto, User.class);
        Comment comment = this.modelMapper.map(commentDto, Comment.class);
        comment.setPost(post);
        comment.setUser(user);
        Comment savedComment = this.commentRepo.save(comment);
        return this.modelMapper.map(savedComment, CommentDto.class);
    }

    @Override
    public void deleteComment(Integer commentID) {
        Comment comment = this.commentRepo
                .findById(commentID)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentID));
        this.commentRepo.delete(comment);
    }
}
