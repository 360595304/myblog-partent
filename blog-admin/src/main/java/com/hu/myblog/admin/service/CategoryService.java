package com.hu.myblog.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hu.myblog.admin.entity.Category;
import com.hu.myblog.admin.modle.params.PageParam;

import java.util.List;

public interface CategoryService extends IService<Category> {
    Page<Category> getCategoryPage(PageParam pageParam);
}
