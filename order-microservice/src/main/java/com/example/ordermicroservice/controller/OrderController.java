package com.example.ordermicroservice.controller;

import com.example.ordermicroservice.dto.OrderDto;
import com.example.ordermicroservice.jpa.OrderEntity;
import com.example.ordermicroservice.service.OrderService;
import com.example.ordermicroservice.vo.RequestOrder;
import com.example.ordermicroservice.vo.ResponseOrder;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/order-service/")
@RequiredArgsConstructor
@RestController
public class OrderController {

    private final OrderService orderService;
    private final Environment env;

    @GetMapping("/health_check")
    public String status() {
        return String.format("It's working Order-service on PORT %s", env.getProperty("local.server.port"));
    }

    @PostMapping("/{user_id}/orders")
    public ResponseEntity<ResponseOrder> createOrder(@PathVariable String user_id, @RequestBody RequestOrder order) {
        OrderDto orderDto = new ModelMapper().map(order, OrderDto.class);
        orderDto.setUserId(user_id);
        OrderDto crOrderDto = orderService.createOrder(orderDto);
        ResponseOrder responseOrder = new ModelMapper().map(crOrderDto, ResponseOrder.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseOrder);
    }

    @GetMapping("/{user_id}/orders")
    public ResponseEntity<List<ResponseOrder>> getOrder(@PathVariable String user_id) {
        Iterable<OrderEntity> orderList = orderService.getOrdersByUserId(user_id);
        List<ResponseOrder> result = new ArrayList<>();
        orderList.forEach(o -> {result.add(new ModelMapper().map(o, ResponseOrder.class));});

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
