package com.example.ding.service.Impl;

import com.example.ding.entity.Order;
import com.example.ding.mapper.OrderMapper;
import com.example.ding.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    public void insertOrder(Order order){
         orderMapper.insertOrder(order);
    }

    @Override
    public int selectIdbyuser(int user_id) {
        return orderMapper.selectIdbyuser(user_id);
    }

    @Override
    public void updataOrderPriceByid(int order_id,int total_price) {
        orderMapper.updataOrderPriceByid(order_id,total_price);
    }

    @Override
    public double selectPriceByid(int oder_id) {
        return orderMapper.selectPriceByid(oder_id);
    }

}
