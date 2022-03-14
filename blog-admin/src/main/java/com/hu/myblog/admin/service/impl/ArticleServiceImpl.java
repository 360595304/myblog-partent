package com.hu.myblog.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hu.myblog.admin.entity.Article;
import com.hu.myblog.admin.entity.Tag;
import com.hu.myblog.admin.mapper.ArticleMapper;
import com.hu.myblog.admin.modle.params.ArticleParam;
import com.hu.myblog.admin.service.ArticleService;
import com.hu.myblog.admin.service.CategoryService;
import com.hu.myblog.admin.service.SysUserService;
import com.hu.myblog.admin.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author suhu
 * @createDate 2022/3/7
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private TagService tagService;

    @Autowired
    private CategoryService categoryService;


    @Override
    public List<Article> findArticleByUserId(Long id) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getAuthorId, id);
        return articleMapper.selectList(queryWrapper);
    }

    @Override
    public Page<Article> getArticlePage(ArticleParam articleParam) {
        Page<Article> articlePage = new Page<>(articleParam.getCurrentPage(), articleParam.getPageSize());
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(articleParam.getTitle())) {
            queryWrapper.like(Article::getSummary, articleParam.getTitle())
                    .or()
                    .like(Article::getTitle, articleParam.getTitle());
        }
        if (!StringUtils.isEmpty(articleParam.getPublished())) {
            queryWrapper.eq(Article::getPublished, articleParam.getPublished());
        }
        if (!StringUtils.isEmpty(articleParam.getShow())) {
            queryWrapper.eq(Article::getOnShow, articleParam.getShow());
        }
        if (!StringUtils.isEmpty(articleParam.getCreateDate())) {
            queryWrapper.ge(Article::getCreateDate, articleParam.getCreateDate());
        }
        queryWrapper.orderByDesc(Article::getCreateDate);
        queryWrapper.eq(Article::getPublished, 0);
        // 作者、类别、标签
        Page<Article> page = articleMapper.selectPage(articlePage, queryWrapper);
        this.packArticle(page.getRecords());
        return page;
    }


    // 设置作者、类别、标签
    private void packArticle(List<Article> records) {
        for (Article article : records) {
            String nickName = sysUserService.getById(article.getAuthorId()).getNickname();
            article.getParams().put("authorName", nickName);
            String categoryName = categoryService.getById(article.getCategoryId()).getCategoryName();
            article.getParams().put("categoryName", categoryName);
            List<Tag> tags = tagService.findTagsByArticleId(article.getId());
            List<String> tagNames = new ArrayList<>();
            tags.forEach(tag->{
                tagNames.add(tag.getTagName());
            });
            article.getParams().put("tags", tagNames);
        }
    }
}
