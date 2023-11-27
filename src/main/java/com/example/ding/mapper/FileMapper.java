package com.example.ding.mapper;

import com.example.ding.entity.File;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileMapper {
    void save(File file);
    File selectfilebyid(int id);
    Boolean existsbyid(int id);
}
