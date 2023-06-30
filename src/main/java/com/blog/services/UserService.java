package com.blog.services;

import com.blog.payload.UserDto;
import com.blog.response.UserResponse;

public interface UserService {

    UserDto createUser(UserDto userDto);
    UserDto updateUser(UserDto userDto, Integer userId);
    UserDto getUserById(Integer userId);
    UserResponse getAllUser(Integer pageNo, Integer pageSize,String sortBy, String sortDir);
    void deleteUser(Integer userID);

}
