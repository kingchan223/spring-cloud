package com.example.usermicroservice.controller;

import com.example.usermicroservice.dto.UserDto;
import com.example.usermicroservice.service.UserService;
import com.example.usermicroservice.vo.Greeting;
import com.example.usermicroservice.vo.RequestUser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/")
@RestController
public class UserController {

    private final UserService userService;

    private final Greeting greeting;

    @GetMapping("/health_check")
    public String status() {
        return "It's working User-service";
    }

    @GetMapping("/welcome")
    public String welcome() {
        return greeting.getMessage();
    }

    @PostMapping("/users")
    public String createUser(@RequestBody RequestUser user) {

        userService.createUser(new UserDto(user.getEmail(), user.getName(), user.getPwd()));
        return "Create user service is working";
    }
}
