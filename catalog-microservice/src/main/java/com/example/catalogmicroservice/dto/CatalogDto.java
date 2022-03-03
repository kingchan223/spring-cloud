package com.example.catalogmicroservice.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CatalogDto implements Serializable{
    private String projectId;
    private Integer qty;
    private Integer unitPrice;
    private Integer totalPrice;
    private String orderId;
    private String userId;
}
