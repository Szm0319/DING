package com.example.ding.service;

import com.example.ding.entity.Order;
import com.example.ding.entity.OrderDetails;
import com.example.ding.mapper.OrderMapper;
import org.springframework.stereotype.Service;

@Service
public interface OrderService extends OrderMapper {
    void insertOrder(Order order);

    @Override
    int selectIdbyuser(int user_id);

    void updataOrderPriceByid(int order_id,int total_price);
    double selectPriceByid(int oder_id);


}
