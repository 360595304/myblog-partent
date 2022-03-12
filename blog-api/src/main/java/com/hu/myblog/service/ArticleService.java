package com.hu.myblog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hu.myblog.entity.Article;
import com.hu.myblog.vo.ArchiveVo;
import com.hu.myblog.vo.ArticleVo;
import com.hu.myblog.vo.params.ArticleParams;
import com.hu.myblog.vo.params.PageParams;

import java.util.List;


/**
 * @author suhu
 * @createDate 2022/3/7
 */

public interface ArticleService extends IService<Article> {
    Page<ArticleVo> listArticle(PageParams pageParams);

    List<ArticleVo> hotArticles(int limit);

    List<ArticleVo> newArticles(int limit);

    List<ArchiveVo> listArchives();

    ArticleVo findArticleVoById(long id);

    Long publish(ArticleParams articleParams);

    void addCommentCounts(Long articleId);

    Page<ArticleVo> listArticleByUserId(Integer page, Integer size);

    void removeArticleById(Long id);
}
