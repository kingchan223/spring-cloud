package com.example.usermicroservice.controller;

import com.example.usermicroservice.dto.UserDto;
import com.example.usermicroservice.jpa.UserEntity;
import com.example.usermicroservice.service.UserService;
import com.example.usermicroservice.vo.Greeting;
import com.example.usermicroservice.vo.RequestUser;
import com.example.usermicroservice.vo.ResponseUser;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/")
@RestController
public class UserController {

    private final Environment env;
    private final UserService userService;
    private final Greeting greeting;

    @GetMapping("/health_check")
    public String status() {
        return String.format("It's working User-service on PORT %s", env.getProperty("local.server.port"));
    }

    @GetMapping("/welcome")
    public String welcome() {
        return greeting.getMessage();
    }

    @PostMapping("/users")
    public ResponseEntity<ResponseUser> createUser(@RequestBody RequestUser user) {
        UserDto userDto = new ModelMapper().map(user, UserDto.class);
        userService.createUser(userDto);
        ResponseUser responseUser = new ResponseUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }

    @GetMapping("/users/{user_id}")
    public ResponseEntity<ResponseUser> userOne(@PathVariable String user_id) {
        UserDto userDto = userService.getUserById(user_id);
        ResponseUser responseUser = new ResponseUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }

    @GetMapping("/users")
    public ResponseEntity<List<ResponseUser>> userList() {
        Iterable<UserEntity> userList = userService.getUserByAll();
        List<ResponseUser> result = new ArrayList<>();
        userList.forEach(u -> result.add(new ModelMapper().map(u, ResponseUser.class)));
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

}
