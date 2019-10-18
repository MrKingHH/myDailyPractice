package com.java.controller.admin;

import com.github.pagehelper.PageInfo;
import com.java.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @author: SamuelKing
 * @date: 2019/10/17
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private BookService bookService;

    @RequestMapping("/test")
    public @ResponseBody String test() {
        return "hello";
    }

    @RequestMapping("/getBooks")
    public @ResponseBody List<Map<String, Object>> getBooks(Integer pageNum, Integer pageSize) {
        List<Map<String, Object>> books = bookService.findBooks(pageNum, pageSize);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(books);
        return pageInfo.getList();
    }
}
