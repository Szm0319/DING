package com.example.ding.mapper;

import com.example.ding.entity.OrderDetails;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderDetailsMapper {
    void insertOrderDetails(OrderDetails orderDetails);

    int gettotalprice(int order_id);
}
