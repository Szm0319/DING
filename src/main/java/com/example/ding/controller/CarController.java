package com.example.ding.controller;

import com.example.ding.service.CaiService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Controller
public class CarController {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    private HashOperations<String, Object, Object> hashOperations;
    private CaiService caiService;

    public CarController(CaiService caiService) {
        this.caiService = caiService;
    }
    @PostMapping("/car")
    public ResponseEntity<Map<String,String>> addCar(@RequestParam("id")int id, @RequestParam("num")int num, HttpSession session){
        String lockKey = "lock_key_" + id;
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        boolean gotLock = ops.setIfAbsent(lockKey, "locked", 10, TimeUnit.SECONDS);
        Map<String, String> response = new HashMap<>();
        if (!gotLock) {
            response.put("message", "系统繁忙，请稍后再试");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
        try {
            String username = (String) session.getAttribute("USER");
            if (username==null){
                response.put("message","用户未登录");
                return ResponseEntity.badRequest().body(response);
            }

            // 构建Redis中的购物车键，这里使用了哈希结构
            String carKey = "car" + username;
            String cairemain = "cai_remain";
            String cai_name = caiService.selectNameByid(id);
            // 确保id被转换为String
            String idString = String.valueOf(id);
            HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
            Integer remain = (Integer) hashOperations.get(cairemain,idString);

            if (remain == null) {
                // 从数据库查询剩余库存
                remain = caiService.selectRemainByName(cai_name);
                System.out.println("本道菜剩余量："+remain);
                // 将库存信息存储到Redis
                hashOperations.put(cairemain,idString,remain);
            }

            if (remain < num) {
                response.put("message", "库存不足");
                return ResponseEntity.badRequest().body(response);
            }
            // 更新Redis中的购物车数据
            // 如果该商品已经存在于购物车中，它的数量将被累加
            // 如果该商品是新添加的，它将被初始化为传入的数量
            hashOperations.increment(carKey, cai_name, num);
            remain = remain - num;
            hashOperations.put(cairemain,idString,remain);
            caiService.updateByRemainByid(id,remain);
            // 更新model，用于视图显示或进一步的处理
            response.put("message","商品已加入购物车");
            return ResponseEntity.ok().body(response);
        } finally {
            // 释放锁
            redisTemplate.delete(lockKey);
        }

    }
    @PostMapping("/getcarinfo")
    public ResponseEntity<Map<Object,Object>> getcarinfo(Model model,HttpSession session){
        String username = (String)session.getAttribute("USER");
        System.out.println(username);
        String cartKey = "car" + username;
        // 获取Redis哈希操作的引用
        hashOperations = redisTemplate.opsForHash();
        Map<Object, Object> carinfo = hashOperations.entries(cartKey);
        System.out.println(carinfo);

        return ResponseEntity.ok().body(carinfo);

    }
}
