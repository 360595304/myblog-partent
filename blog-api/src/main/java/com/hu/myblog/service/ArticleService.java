package com.hu.myblog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hu.myblog.entity.Article;
import com.hu.myblog.vo.ArticleVo;
import com.hu.myblog.vo.params.PageParams;


/**
 * @author suhu
 * @createDate 2022/3/7
 */

public interface ArticleService extends IService<Article> {
    Page<ArticleVo> listArticle(PageParams pageParams);
}
