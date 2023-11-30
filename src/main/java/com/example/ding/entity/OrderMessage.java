package com.example.ding.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderMessage implements Serializable {
    private int userid;
    private String carkey;
}
