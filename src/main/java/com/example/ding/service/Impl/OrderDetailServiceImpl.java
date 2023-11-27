package com.example.ding.service.Impl;

import com.example.ding.entity.OrderDetails;
import com.example.ding.mapper.OrderDetailsMapper;
import com.example.ding.service.OrderDetailService;
import com.example.ding.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {
    @Autowired
    private OrderDetailsMapper orderDetailsMapper;

    @Override
    public void insertOrderDetails(OrderDetails orderDetails) {
        orderDetailsMapper.insertOrderDetails(orderDetails);
    }

    @Override
    public int gettotalprice(int order_id) {
        return orderDetailsMapper.gettotalprice(order_id);
    }
}
