package com.hu.myblog.admin.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hu.myblog.admin.entity.Tag;
import com.hu.myblog.admin.modle.params.PageParam;

import java.util.List;


public interface TagService extends IService<Tag> {
    public List<Tag> findTagsByArticleId(Long id);

    Page<Tag> getTagPage(PageParam pageParam);
}
