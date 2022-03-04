package com.example.usermicroservice.dto;

import com.example.usermicroservice.vo.ResponseOrder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Data
public class UserDto {
    private String email;
    private String name;
    private String password;
    private String userId;
    private Date createdAt;
    private String encPassword;
    private List<ResponseOrder> orders;
}
