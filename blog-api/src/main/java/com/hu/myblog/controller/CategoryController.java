package com.hu.myblog.controller;

import com.hu.myblog.result.Result;
import com.hu.myblog.service.CategoryService;
import com.hu.myblog.vo.CategoryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author suhu
 * @createDate 2022/3/10
 */
@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/getAll")
    public Result getCategories() {
        List<CategoryVo> categoryVoList = categoryService.getCategoriesVo();
        return Result.ok(categoryVoList);
    }
}
