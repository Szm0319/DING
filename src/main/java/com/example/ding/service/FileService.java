package com.example.ding.service;

import com.example.ding.entity.File;
import com.example.ding.mapper.FileMapper;
import org.springframework.stereotype.Service;

@Service
public interface FileService extends FileMapper {
    void save(File file);

    File selectfilebyid(int id);

    Boolean existsbyid(int id);
}
