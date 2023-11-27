package com.example.ding.mapper;

import com.example.ding.entity.Cai;
import org.apache.ibatis.annotations.Mapper;

import java.awt.print.Pageable;
import java.util.List;


@Mapper
public interface CaiMapper {
    Cai selectById(int id);
    void deleteById(int id);
    void updateById(Cai cai);

    void insertCai(Cai cai);
    List<Cai> selectAll();
    List<Cai> selectByPage(int pageset, int size);

    String selectNameByid(int id);

    int selectPriceByName(String name);

    int selectRemainByName(String name);

    void updateByRemainByid(int id,int remain);
}
