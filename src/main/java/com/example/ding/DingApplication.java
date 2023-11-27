package com.example.ding;

import com.example.ding.entity.Cai;
import com.example.ding.mapper.CaiMapper;
import com.example.ding.service.CaiService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;

import static ch.qos.logback.core.util.StatusPrinter.print;
@SpringBootApplication
@ServletComponentScan
public class DingApplication {
    public static void main(String[] args) {
        SpringApplication.run(DingApplication.class, args);
    }
    @RequestMapping("/hello")
    public String hello(){
        return "hello111";
    }
}
