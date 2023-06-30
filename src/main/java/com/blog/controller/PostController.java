package com.blog.controller;

import com.blog.response.ApiResponse;
import com.blog.payload.PostDto;
import com.blog.response.PostResponse;
import com.blog.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId) {
        PostDto postDto = this.postService.getPostById(postId);
        return ResponseEntity.ok(postDto);
    }

    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPost(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
        PostResponse postResponse = this.postService.getAllPost(pageNo, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(postResponse);
    }

    @DeleteMapping("posts/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId) {
        this.postService.deletePost(postId);
        return new ResponseEntity<>(new ApiResponse("Post Deleted", true), HttpStatus.OK);
    }


    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto,
                                              @PathVariable Integer userId,
                                              @PathVariable Integer categoryId) {
        PostDto postDtoRes = this.postService.createPost(postDto, userId, categoryId);
        return ResponseEntity.ok(postDtoRes);
    }

    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto,
                                              @PathVariable Integer postId) {
        PostDto postDtoRes = this.postService.updatePost(postDto, postId);
        return ResponseEntity.ok(postDtoRes);
    }

    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Integer categoryId) {
        List<PostDto> postDtoList = this.postService.getPostsByCategory(categoryId);
        return ResponseEntity.ok(postDtoList);
    }

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId) {
        List<PostDto> postDtoList = this.postService.getPostsByUser(userId);
        return ResponseEntity.ok(postDtoList);
    }

    @GetMapping("/posts/search/{title}")
    public ResponseEntity<List<PostDto>> getPostByTitle(@PathVariable String title){
        List<PostDto> postDtoList = this.postService.searchPosts(title);
        return ResponseEntity.ok(postDtoList);
    }

}
