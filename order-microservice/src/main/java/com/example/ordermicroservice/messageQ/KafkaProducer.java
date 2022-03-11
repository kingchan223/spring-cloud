package com.example.ordermicroservice.messageQ;

import com.example.ordermicroservice.dto.OrderDto;
import com.example.ordermicroservice.jpa.OrderEntity;
import com.example.ordermicroservice.jpa.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
@Service
public class KafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public OrderDto send(String topic, OrderDto orderDto) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = "";
        try{
            jsonInString = mapper.writeValueAsString(orderDto);
        }catch(JsonProcessingException ex){
            ex.printStackTrace();
        }
        kafkaTemplate.send(topic, jsonInString);
        log.info("Kafka Producer sent data from Order Service:{}", orderDto);
        return orderDto;
    }

}
