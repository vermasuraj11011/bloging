package com.blog.response;

import com.blog.payload.CategoryDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {
    private List<CategoryDto> content;
    private Integer pageNo;
    private Integer pageSize;
    private Integer totalPages;
    private Long totalElement;
    private boolean isLastPage;
}
