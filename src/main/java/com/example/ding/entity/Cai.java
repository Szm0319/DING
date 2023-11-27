package com.example.ding.entity;

import jdk.jfr.DataAmount;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
@Data
public class Cai {
    private int id;
    private int price;
    private String name;
    private String fenlei;
    private String image;
    private int zk;
    private double zkprice;
}
