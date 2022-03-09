package com.hu.myblog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hu.myblog.entity.ArticleBody;
import com.hu.myblog.mapper.ArticleBodyMapper;
import com.hu.myblog.service.ArticleBodyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author suhu
 * @createDate 2022/3/9
 */
@Service
public class ArticleBodyServiceImpl extends ServiceImpl<ArticleBodyMapper, ArticleBody> implements ArticleBodyService {
    @Autowired
    private ArticleBodyMapper articleBodyMapper;
}
