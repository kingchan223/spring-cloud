package com.example.usermicroservice.client;

import com.example.usermicroservice.vo.ResponseOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name="order-service")//application name
public interface OrderServiceClient {

    @GetMapping("/order-service/{userId}/orders")//고의적으로 잘못된 주소 order_ng사용
    List<ResponseOrder>  getOrders(@PathVariable String userId);
}
