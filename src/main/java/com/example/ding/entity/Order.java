package com.example.ding.entity;

import lombok.Data;
import java.time.LocalDateTime;
@Data
public class Order {
    private int order_id;
    private int user_id;
    private int total_price;
    private String status;
    private LocalDateTime order_date;
}
