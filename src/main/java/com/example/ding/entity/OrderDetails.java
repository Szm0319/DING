package com.example.ding.entity;

import lombok.Data;

@Data
public class OrderDetails {
    private int order_detail_id;
    private int order_id;
    private String cai_name;
    private int quantity;
    private int price;
}
