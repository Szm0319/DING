package com.example.ding.service;

import com.example.ding.entity.Cai;
import com.example.ding.mapper.CaiMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CaiService extends CaiMapper{
    @Override
    Cai selectById(int id);

    @Override
    void deleteById(int id);

    @Override
    void insertCai(Cai cai);

    @Override
    void updateById(Cai cai);

    @Override
    List<Cai> selectAll();

    List<Cai> selectByPage(int pageset, int size);

    @Override
    String selectNameByid(int id);

    int selectPriceByName(String name);

}
