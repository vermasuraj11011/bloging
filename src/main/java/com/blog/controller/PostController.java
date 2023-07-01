package com.blog.controller;

import com.blog.config.AppConstants;
import com.blog.response.ApiResponse;
import com.blog.payload.PostDto;
import com.blog.response.FileResponse;
import com.blog.response.PostResponse;
import com.blog.services.FileService;
import com.blog.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId) {
        PostDto postDto = this.postService.getPostById(postId);
        return ResponseEntity.ok(postDto);
    }

    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPost(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.PAGE_NO, required = false) Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {
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
    public ResponseEntity<List<PostDto>> getPostByTitle(@PathVariable String title) {
        List<PostDto> postDtoList = this.postService.searchPosts(title);
        return ResponseEntity.ok(postDtoList);
    }

//    upload image to the image folder
    @PostMapping("/image/upload")
    public ResponseEntity<FileResponse> fileUpload(
            @RequestParam MultipartFile image
    ) {
        String fileName = null;
        try {
            fileName = this.fileService.uploadImage(path, image);
        } catch (Exception e) {
            e.printStackTrace();
            FileResponse fileResponse =
                    new FileResponse(null, "File upload unsuccessful :" + e.getMessage());
            return new ResponseEntity<>(fileResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        FileResponse fileResponse = new FileResponse(fileName, "File uploaded successfully");
        return new ResponseEntity<>(fileResponse, HttpStatus.OK);
    }

//    download image from the image folder and sent in the response
    @GetMapping(value = "/image/{fileName}", produces = MediaType.IMAGE_PNG_VALUE)
    public void downloadImage(
            @PathVariable String fileName,
            HttpServletResponse response
    ) throws IOException {
        InputStream resource = this.fileService.getResources(path, fileName);
        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }

//    upload a image in the post
    @PostMapping(value = "/post/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadPostImage(
            @RequestBody MultipartFile image,
            @PathVariable Integer postId
    ) throws Exception {
        PostDto postDto = this.postService.getPostById(postId);
        String imageName = this.fileService.uploadImage(path, image);
        postDto.setImageName(imageName);
        PostDto updatedDto = this.postService.updatePost(postDto, postId);
        return ResponseEntity.ok(updatedDto);
    }
}
