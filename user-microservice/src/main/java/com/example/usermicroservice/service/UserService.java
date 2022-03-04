package com.example.usermicroservice.service;


import com.example.usermicroservice.dto.UserDto;
import com.example.usermicroservice.jpa.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    void createUser(UserDto userDto);
    UserDto getUserById(String userId);
    Iterable<UserEntity> getUserByAll();
    UserDto getUserDetailsByEmail(String username);
}
