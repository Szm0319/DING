package com.example.ding.controller;

import com.example.ding.entity.User;
import com.example.ding.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
public class loginController {
    @Autowired
    private UserService userService;
    @GetMapping("/index")
    public String index2(){
        return "index";
    }
    @GetMapping({"/", "/login"})
    public String index(){
        return "login";
    }
    @GetMapping("/register")
    public String register(){
        return "register";
    }
    @PostMapping("/toregister")
    public String toregister(@RequestParam("username")String username,
                                                         @RequestParam("password")String password,
                                                    @RequestParam("usertype")String usertype,
                                                    Model model){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setUsertype(usertype);
        System.out.println(user);
        userService.save(user);
        model.addAttribute("message","注册账号成功，请继续登录");
        return "login";
    }
    @GetMapping("/session-timeout")
    public String getSessionTimeout(HttpSession session) {
        System.out.println(session.getMaxInactiveInterval());
        return "index";
    }
    @PostMapping("/login")
    public String login(@RequestParam("username")String username,
                        @RequestParam("password")String password,
                        HttpSession session,
                        Model model) {
        Boolean havaname = userService.selectUserByUsername(username);
        if (havaname) {
            String pd = userService.selectpassword(username);
            String usertype = userService.selectUsertype(username);
            if (pd.equals(password)) {
                session.setAttribute("USER",username);
                session.setAttribute("USERTYPE",usertype);
                // 设置购物车初始值。这可以是一个简单的计数器或更复杂的数据结构
                session.setAttribute("CART", new HashMap<Integer,Integer>());
                model.addAttribute("status", "200");
                model.addAttribute("message","登录成功");
                return "index";
            }
            else{
                model.addAttribute("message","密码错误");
                return "login";
            }
        } else {
            model.addAttribute("message", "账号不存在");
            return "login";
        }
    }
}
