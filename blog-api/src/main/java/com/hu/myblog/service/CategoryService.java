package com.hu.myblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hu.myblog.entity.Category;
import com.hu.myblog.vo.CategoryVo;

import java.util.List;

public interface CategoryService extends IService<Category> {
    // 获取所有分类
    List<CategoryVo> getCategoriesVo();
}
