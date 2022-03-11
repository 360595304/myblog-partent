package com.hu.myblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hu.myblog.entity.*;
import com.hu.myblog.handler.MyException;
import com.hu.myblog.mapper.ArticleMapper;
import com.hu.myblog.result.ErrorCode;
import com.hu.myblog.service.*;
import com.hu.myblog.utils.UserThreadLocal;
import com.hu.myblog.vo.*;
import com.hu.myblog.vo.params.ArticleParams;
import com.hu.myblog.vo.params.PageParams;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
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

    @Autowired
    private ArticleTagService articleTagService;


    @Override
    @Cacheable(value = "article", keyGenerator = "keyGenerator")
    public Page<ArticleVo> listArticle(PageParams pageParams) {
        Page<Article> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        if (pageParams.getCategoryId() != null) {
            queryWrapper.eq("category_id", pageParams.getCategoryId());
        }
        if (pageParams.getTagId() != null) {
            List<ArticleTag> articleTagList = articleTagService.findByTagId(pageParams.getTagId());
            List<Long> articleIds = new ArrayList<>();
            for (ArticleTag articleTag : articleTagList) {
                articleIds.add(articleTag.getArticleId());
            }
            queryWrapper.in("id", articleIds);
        }
        if (pageParams.getYear() != null) {
            queryWrapper.eq("year(create_date)", pageParams.getYear());
        }
        if (pageParams.getMonth() != null) {
            queryWrapper.eq("month(create_date)", pageParams.getMonth());
        }
        queryWrapper.orderByDesc("weight", "create_date");
        Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);
        List<ArticleVo> articleVoList = this.copyList(articlePage.getRecords(), true, true);
        Page<ArticleVo> articleVoPage = new Page<>(page.getCurrent(), page.getSize());
        articleVoPage.setRecords(articleVoList);
        articleVoPage.setPages(page.getPages());
        articleVoPage.setTotal(page.getTotal());
        articleVoPage.setSize(pageParams.getPageSize());
        return articleVoPage;
    }

    @Override
    @Cacheable(value = "article", keyGenerator = "keyGenerator")
    public List<ArticleVo> hotArticles(int limit) {
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id", "title")
                .orderByDesc("view_counts").last("limit " + limit);
        List<Article> articleList = articleMapper.selectList(queryWrapper);
        return copyList(articleList, false, false);
    }

    @Override
    @Cacheable(value = "article", keyGenerator = "keyGenerator")
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
        if (article == null) {
            throw new MyException(ErrorCode.TOKEN_ERROR);
        }
        threadService.updateArticleViewCount(articleMapper, article);
        return this.copy(article, true, true, true, true);
    }

    @Override
    @Transactional
    public Long publish(ArticleParams articleParams) {
        SysUser user = UserThreadLocal.get();
        Article article = new Article();
        article.setCommentCounts(Article.Article_Common);
        article.setWeight(0);
        article.setViewCounts(0);
        article.setTitle(articleParams.getTitle());
        article.setSummary(articleParams.getSummary());
        article.setCreateDate(new Date());
        article.setCommentCounts(0);
        article.setAuthorId(user.getId());
        CategoryVo category = articleParams.getCategory();
        if (category != null) {
            Long categoryId = category.getId();
            article.setCategoryId(categoryId);
        }
        articleMapper.insert(article);
        List<TagVo> tags = articleParams.getTags();
        Long articleId = article.getId();
        if (tags != null) {
            tags.forEach(tag -> {
                ArticleTag articleTag = new ArticleTag();
                articleTag.setArticleId(articleId);
                articleTag.setTagId(tag.getId());
                articleTagService.save(articleTag);
            });
        }
        ArticleBody articleBody = new ArticleBody();
        articleBody.setArticleId(articleId);
        articleBody.setContent(articleParams.getBody().getContent());
        articleBody.setContentBody(articleParams.getBody().getHtmlContent());
        articleBodyService.save(articleBody);
        article.setBodyId(articleBody.getId());
        articleMapper.updateById(article);
        return articleId;
    }

    @Override
    public void addCommentCounts(Long articleId) {
        articleMapper.addCommentCounts(articleId);
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
