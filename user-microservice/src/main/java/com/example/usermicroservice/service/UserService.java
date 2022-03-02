package com.example.usermicroservice.service;


import com.example.usermicroservice.dto.UserDto;
import com.example.usermicroservice.jpa.UserEntity;

public interface UserService {
    UserDto createUser(UserDto userDto);
    UserDto getUserById(String userId);
    Iterable<UserEntity> getUserByAll();
}
