package com.java.services.impl;

import com.github.pagehelper.PageHelper;
import com.java.mapper.BookMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author: SamuelKing
 * @date: 2019/10/18
 */
@Service
public class BookServiceImpl implements com.java.services.BookService {
    @Autowired
    private BookMapper bookMapper;

    //查询所有
    @Override
    public List<Map<String, Object>> findBooks(Integer pageNum, Integer pageSize) {
        //分页
        PageHelper.startPage(pageNum, pageSize);
        return bookMapper.selectBooks();
    }
}
