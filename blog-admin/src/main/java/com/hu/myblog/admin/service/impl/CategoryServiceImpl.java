package com.hu.myblog.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hu.myblog.admin.entity.Category;
import com.hu.myblog.admin.mapper.CategoryMapper;
import com.hu.myblog.admin.modle.params.PageParam;
import com.hu.myblog.admin.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author suhu
 * @createDate 2022/3/9
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;


    @Override
    public Page<Category> getCategoryPage(PageParam pageParam) {
        Page<Category> page = new Page<>(pageParam.getCurrentPage(), pageParam.getPageSize());
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(pageParam.getQueryString())) {
            queryWrapper.like(Category::getCategoryName, pageParam.getQueryString());
        }
        return categoryMapper.selectPage(page, queryWrapper);
    }
}
