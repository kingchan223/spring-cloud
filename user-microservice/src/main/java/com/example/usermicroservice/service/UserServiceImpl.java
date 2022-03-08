package com.example.usermicroservice.service;

import com.example.usermicroservice.client.OrderServiceClient;
import com.example.usermicroservice.dto.UserDto;
import com.example.usermicroservice.jpa.UserEntity;
import com.example.usermicroservice.jpa.UserRepository;
import com.example.usermicroservice.vo.ResponseOrder;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final OrderServiceClient orderServiceClient;
//    private final RestTemplate restTemplate;
    private final Environment env;

    @Override
    public void createUser(UserDto userDto) {
        userDto.setUserId(UUID.randomUUID().toString());
        UserEntity userEntity = UserEntity.create(userDto);
        userEntity.setEncPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(userEntity);
        new ModelMapper().map(userEntity, UserDto.class);
    }

    @Override
    public UserDto getUserById(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);
        UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);
//        /* Using as RestTemplate */
//        String orderUrl = String.format(Objects.requireNonNull(env.getProperty("order_service.url")), userId);
//        ResponseEntity<List<ResponseOrder>> orderListResponse = restTemplate.exchange(orderUrl, HttpMethod.GET, null,
//                new ParameterizedTypeReference<>() {});
//        List<ResponseOrder> orderList = orderListResponse.getBody();
        /*Using Feign Client*/
        List<ResponseOrder> orderList = orderServiceClient.getOrders(userId);
        userDto.setOrders(orderList);
        return userDto;
    }

    @Override
    public Iterable<UserEntity> getUserByAll() {
        return userRepository.findAll();
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(username);
        if(userEntity == null ) throw new UsernameNotFoundException("사용자가 존재하지 않습니다.");
        return new User(userEntity.getEmail(), userEntity.getEncPassword(),
                true,
                true,
                true,
                true, new ArrayList<>());
    }

    @Override
    public UserDto getUserDetailsByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);
        return new ModelMapper().map(userEntity, UserDto.class);
    }
}