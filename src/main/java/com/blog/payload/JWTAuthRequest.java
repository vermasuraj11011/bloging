package com.blog.payload;

import lombok.Data;

@Data
public class JWTAuthRequest {

    private String userName;
    private String password;
}
