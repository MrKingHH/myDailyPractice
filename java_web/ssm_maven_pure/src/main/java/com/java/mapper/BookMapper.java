package com.java.mapper;

import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author: SamuelKing
 * @date: 2019/10/18
 */
public interface BookMapper {

    //查询所有
    @Select("SELECT * from books")
    List<Map<String, Object>> selectBooks();
}
