package com.example.ding.service.Impl;

import com.example.ding.entity.User;
import com.example.ding.mapper.UserMapper;
import com.example.ding.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public void save(User user) {
        userMapper.save(user);
    }
    public Boolean selectUserByUsername(String username){
        return userMapper.selectUserByUsername(username);
    }
    public String selectpassword(String username){
        return userMapper.selectpassword(username);
    }
    public String selectUsertype(String username){
        return userMapper.selectUsertype(username);
    }
    public Integer selectUserID(String username){
        return userMapper.selectUserID(username);
    }
}
