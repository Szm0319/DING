package com.example.ding.service.Impl;

import com.example.ding.entity.File;
import com.example.ding.mapper.FileMapper;
import com.example.ding.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private FileMapper fileMapper;

    @Override
    public void save(File file) {
        fileMapper.save(file);
    }

    public File selectfilebyid(int id){
       return fileMapper.selectfilebyid(id);
    }

    @Override
    public Boolean existsbyid(int id) {
        return fileMapper.existsbyid(id);
    }


}
