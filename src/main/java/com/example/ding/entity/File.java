package com.example.ding.entity;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class File {
    private int cai_id;
    private String filename;
    private byte[] filedata;
}