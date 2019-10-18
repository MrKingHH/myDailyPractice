package com.java.services;

import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author: SamuelKing
 * @date: 2019/10/18
 */
public interface BookService {
    //查询所有
    List<Map<String, Object>> findBooks(Integer pageNum, Integer pageSize);
}
