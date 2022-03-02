package com.example.usermicroservice.jpa;

import com.example.usermicroservice.dto.UserDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
@Table(name="users")
public class UserEntity {
    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length=50, unique=true)
    private String email;

    @Column(nullable = false, length=50)
    private String name;

    @Column(nullable = false, unique=true)
    private String userId;

    @Column(nullable = false, unique=true)
    private String encPwd;

    public static UserEntity create(UserDto userDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(userDto.getEmail());
        userEntity.setName(userDto.getName());
        userEntity.setUserId(userDto.getUserId());
        userEntity.setEncPwd("enc_"+userDto.getPwd());
        return userEntity;
    }
}
