package com.example.ding.mapper;

import com.example.ding.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    void save(User user);
    Boolean selectUserByUsername(String username);

    String selectpassword(String username);
    String selectUsertype(String username);

    Integer selectUserID(String username);
}
