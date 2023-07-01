package com.blog.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    private int id;

    @NotEmpty
    @Size(min = 4, message = "Name should be not empty and minimum of 4 character")
    private String name;

    @Email(message = "Email is not valid")
    private String email;

    @NotEmpty
    @Size(min = 4,message = "Password should be more than 4 character")
    @JsonIgnore
    private String password;

    @NotEmpty
    private String about;
}
