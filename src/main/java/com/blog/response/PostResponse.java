package com.blog.response;

import com.blog.payload.PostDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {
    private List<PostDto> content;
    private Integer pageNo;
    private Integer pageSize;
    private Integer totalPages;
    private Long totalElement;
    private boolean isLastPage;
}
