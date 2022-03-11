package com.hu.myblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hu.myblog.entity.ArticleTag;

import java.util.List;

public interface ArticleTagService extends IService<ArticleTag> {
    List<ArticleTag> findByTagId(Long tagId);
}
