package com.example.ding.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    private int userid;
    private String carkey;
}
