package com.blog.services;

import com.blog.payload.CategoryDto;
import com.blog.response.CategoryResponse;

public interface CategoryService {
    CategoryDto createCategory(CategoryDto categoryDto);
    CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);
    CategoryDto getCategoryById(Integer categoryId);
    CategoryResponse getAllCategory(Integer pageNo, Integer pageSize,String sortBy, String sortDir);
    void deleteCategory(Integer categoryId);
}
