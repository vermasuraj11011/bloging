package com.blog.controller;

import com.blog.response.ApiResponse;
import com.blog.payload.CategoryDto;
import com.blog.response.CategoryResponse;
import com.blog.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        CategoryDto createdcategoryDto = this.categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(createdcategoryDto, HttpStatus.CREATED);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,
                                                      @PathVariable Integer categoryId) {
        CategoryDto updateCategory = this.categoryService.updateCategory(categoryDto, categoryId);
        return ResponseEntity.ok(updateCategory);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer categoryId) {
        this.categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(new ApiResponse("Category Deleted", true), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<CategoryResponse> getAllCategories(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
        CategoryResponse categoryResponse = this.categoryService.getAllCategory(pageNo, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(categoryResponse);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Integer categoryId) {
        return ResponseEntity.ok(this.categoryService.getCategoryById(categoryId));
    }

}
