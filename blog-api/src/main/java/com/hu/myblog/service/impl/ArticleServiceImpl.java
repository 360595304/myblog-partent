package com.hu.myblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hu.myblog.entity.Article;
import com.hu.myblog.entity.ArticleBody;
import com.hu.myblog.entity.Category;
import com.hu.myblog.mapper.ArticleMapper;
import com.hu.myblog.service.*;
import com.hu.myblog.vo.*;
import com.hu.myblog.vo.params.PageParams;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private TagService tagService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private ArticleBodyService articleBodyService;

    @Autowired
    private CategoryService categoryService;


    @Override
    public Page<ArticleVo> listArticle(PageParams pageParams) {
        Page<Article> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("weight", "create_date");
        Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);
        List<ArticleVo> articleVoList = this.copyList(articlePage.getRecords(), true, true);
        Page<ArticleVo> articleVoPage = new Page<>(page.getCurrent(), page.getSize());
        articleVoPage.setRecords(articleVoList);
        articleVoPage.setPages(page.getPages());
        articleVoPage.setTotal(page.getTotal());
        return articleVoPage;
    }

    @Override
    public List<ArticleVo> hotArticles(int limit) {
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id", "title")
                .orderByDesc("view_counts").last("limit " + limit);
        List<Article> articleList = articleMapper.selectList(queryWrapper);
        return copyList(articleList, false, false);
    }

    @Override
    public List<ArticleVo> newArticles(int limit) {
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id", "title")
                .orderByDesc("create_date").last("limit " + limit);
        List<Article> articleList = articleMapper.selectList(queryWrapper);
        return copyList(articleList, false, false);
    }

    @Override
    public List<ArchiveVo> listArchives() {
        return articleMapper.listArchives();
    }

    @Autowired
    private ThreadService threadService;

    @Override
    public ArticleVo findArticleVoById(long id) {
        Article article = articleMapper.selectById(id);
        threadService.updateArticleViewCount(articleMapper, article);
        return this.copy(article, true, true, true, true);
    }

    // 转换成vo
    private List<ArticleVo> copyList(List<Article> articleList, boolean isTag, boolean isAuthor) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        articleList.forEach(article -> {
            articleVoList.add(this.copy(article, isTag, isAuthor, false, false));
        });
        return articleVoList;
    }

    private ArticleVo copy(Article article, boolean isTag, boolean isAuthor, boolean isBody, boolean isCategory) {
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article, articleVo);
        if (isTag) {
            articleVo.setTags(tagService.findTagsByArticleId(article.getId()));
        }
        if (isAuthor) {
            Long authorId = article.getAuthorId();
            articleVo.setAuthor(sysUserService.getById(authorId).getNickname());
        }
        if (isBody) {
            ArticleBody articleBody = articleBodyService.getById(article.getBodyId());
            ArticleBodyVo articleBodyVo = new ArticleBodyVo();
            articleBodyVo.setContent(articleBody.getContent());
            articleVo.setBody(articleBodyVo);
        }
        if (isCategory) {
            Category category = categoryService.getById(article.getCategoryId());
            CategoryVo categoryVo = new CategoryVo();
            BeanUtils.copyProperties(category, categoryVo);
            articleVo.setCategory(categoryVo);
        }
        return articleVo;
    }
}
