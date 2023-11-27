package com.example.ding.service.Impl;

import com.example.ding.entity.Cai;
import com.example.ding.mapper.CaiMapper;
import com.example.ding.service.CaiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class CaiServiceImpl implements CaiService {
    @Autowired
    private CaiMapper caiMapper;
    @Override
    public Cai selectById(int id) {
        return caiMapper.selectById(id);
    }


    @Override
    public void deleteById(int id) {
        caiMapper.deleteById(id);
    }

    @Override
    public void updateById(Cai cai) {
        caiMapper.updateById(cai);
    }

    @Override
    public void insertCai(Cai cai) {
        caiMapper.insertCai(cai);
    }

    @Override
    public List<Cai> selectAll() {
        return caiMapper.selectAll();
    }
    public List<Cai> selectByPage ( int pageset, int size){
        return caiMapper.selectByPage(pageset,size);
    }

    @Override
    public String selectNameByid(int id) {
        return caiMapper.selectNameByid(id);
    }

    @Override
    public int selectPriceByName(String name) {
        return caiMapper.selectPriceByName(name);
    }

    public int selectRemainByName(String name){
        return caiMapper.selectRemainByName(name);
    }

    public void updateByRemainByid(int id,int remain){
        caiMapper.updateByRemainByid(id,remain);
    }
}
