package com.blog.payload;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDto {

    private Integer id;

    @NotEmpty
    @Size(min = 4, message = "Name should be not empty and minimum of 4 character")
    private String categoryTitle;

    @NotEmpty
    private String categoryDescription;
}
