package com.hu.myblog.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hu.myblog.admin.entity.Article;
import com.hu.myblog.admin.modle.params.ArticleParam;
import com.hu.myblog.admin.modle.params.PageParam;

import java.util.List;


/**
 * @author suhu
 * @createDate 2022/3/7
 */

public interface ArticleService extends IService<Article> {

    List<Article> findArticleByUserId(Long id);

    Page<Article> getArticlePage(ArticleParam articleParam);
}
