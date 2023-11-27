package com.example.ding.service;

import com.example.ding.entity.OrderDetails;
import com.example.ding.mapper.OrderDetailsMapper;
import org.springframework.stereotype.Service;

@Service
public interface OrderDetailService extends OrderDetailsMapper {
    void insertOrderDetails(OrderDetails orderDetails);

    int gettotalprice(int order_id);


}
