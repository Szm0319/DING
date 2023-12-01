package com.example.ding.controller;

import com.example.ding.entity.Order;
import com.example.ding.entity.OrderDetails;
import com.example.ding.entity.OrderMessage;
import com.example.ding.service.CaiService;
import com.example.ding.service.OrderDetailService;
import com.example.ding.service.OrderService;
import com.example.ding.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Controller
@Slf4j
public class OrderController {

    private OrderService orderService;
    private OrderDetailService orderDetailService;
    private UserService userService;
    private RedisTemplate<String,Object> redisTemplate;
    private HashOperations<String, Object, Object> hashOperations;
    private CaiService caiService;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    public OrderController(OrderService orderService, OrderDetailService orderDetailService, UserService userService, RedisTemplate<String, Object> redisTemplate, CaiService caiService) {
        this.orderService = orderService;
        this.orderDetailService = orderDetailService;
        this.userService = userService;
        this.redisTemplate = redisTemplate;
        this.caiService = caiService;
    }

   /* @PostMapping("/createOrder")
    public ResponseEntity<Map<String,Object>> createOrder( HttpSession session) throws InterruptedException {
        String username = (String) session.getAttribute("USER");
        if (username == null) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "用户未登录");
            return ResponseEntity.badRequest().body(response);
        }
        Map<String,Object> response = new HashMap<>();
        System.out.println(username);
        int userid = userService.selectUserID(username);
        String carkey = "car" + username;
        System.out.println(userid);
        log.info(username);
        // 创建消息对象
        OrderMessage orderMessage = new OrderMessage();
        orderMessage.setCarkey(carkey);
        orderMessage.setUserid(userid);
        String exchangeName = "Orders";
        String routingKey = "newOrder";
        // 发送消息
        rabbitTemplate.convertAndSend(exchangeName, routingKey, orderMessage);
        response.put("message","订单已创建");
        return ResponseEntity.ok().body(response);
    }*/

    @PostMapping("/createOrder")
    public ResponseEntity<Map<String,Object>> createOrder( HttpSession session){
        String username = (String) session.getAttribute("USER");
        if (username==null){
            Map<String,Object> response = new HashMap<>();
            response.put("message","用户未登录");
            return ResponseEntity.badRequest().body(response);
        }
        System.out.println(username);
        int userid = userService.selectUserID(username);
        System.out.println(userid);
        Order order = new Order();
        order.setUser_id(userid);
        order.setStatus("已提交订单");
        order.setOrder_date(LocalDateTime.now());
        System.out.println(LocalDateTime.now());
        orderService.insertOrder(order);
        int orderid = orderService.selectIdbyuser(userid);
        System.out.println(orderid);
        String carkey = "car" + username;
        hashOperations = redisTemplate.opsForHash();
        Map<Object,Object> car = hashOperations.entries(carkey);
        int total_price = 0;
        for (Map.Entry<Object,Object> entry : car.entrySet()){
            String cai_name = (String) entry.getKey();
            int quantity = (int) entry.getValue();
            OrderDetails orderDetails = new OrderDetails();
            System.out.println(orderid);
            orderDetails.setOrder_id(orderid);
            orderDetails.setCai_name(cai_name);
            orderDetails.setQuantity(quantity);
            int price = caiService.selectPriceByName(cai_name);
            orderDetails.setPrice(price);
            orderDetailService.insertOrderDetails(orderDetails);
            int item_price = quantity * price;
            total_price = total_price+item_price;
        }
        System.out.println("总价格为："+total_price);
//        int totalprice = orderDetailService.gettotalprice(orderid);
        orderService.updataOrderPriceByid(orderid,total_price);
        redisTemplate.delete(carkey);
        Map<String,Object> response = new HashMap<>();
        response.put("message","订单已创建");
        response.put("total",total_price);
        return ResponseEntity.ok().body(response);
    }

}
