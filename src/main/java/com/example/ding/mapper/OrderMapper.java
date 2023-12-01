package com.example.ding.mapper;

import com.example.ding.entity.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {
    void insertOrder(Order order);
    int selectIdbyuser(int user_id);

    void updataOrderPriceByid(int order_id,int total_price);
    double selectPriceByid(int oder_id);
}
