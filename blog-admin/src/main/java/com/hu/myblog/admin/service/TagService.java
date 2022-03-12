package com.hu.myblog.admin.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.hu.myblog.admin.entity.Tag;

import java.util.List;


public interface TagService extends IService<Tag> {
    public List<Tag> findTagsByArticleId(Long id);
}
