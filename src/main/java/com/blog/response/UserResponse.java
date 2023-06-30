package com.blog.response;

import com.blog.payload.UserDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserResponse {
    private List<UserDto> content;
    private Integer pageNo;
    private Integer pageSize;
    private Integer totalPages;
    private Long totalElement;
    private boolean isLastPage;
}
