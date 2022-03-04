package com.example.usermicroservice.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RequestLogin {
    @NotNull(message="Email cannot be null")
    @Size(min=2, message = "이메일은 2자 이상")
    private String email;
    @NotNull(message="Password cannot be null")
    @Size(min=2, message = "이메일은 8자 이상")
    private String password;

}
