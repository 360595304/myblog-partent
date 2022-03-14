package com.hu.myblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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

    @Autowired
    private CommentsService commentsService;

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
        queryWrapper.eq("on_show", 1);
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
                .eq("on_show", 1)
                .orderByDesc("view_counts").last("limit " + limit);
        List<Article> articleList = articleMapper.selectList(queryWrapper);
        return copyList(articleList, false, false);
    }

    @Override
    @Cacheable(value = "article", keyGenerator = "keyGenerator")
    public List<ArticleVo> newArticles(int limit) {
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id", "title")
                .eq("on_show", 1)
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
            throw new MyException(ErrorCode.ARTICLE_NOT_FOUND);
        }
        if (article.getOnShow() == 0) {
            throw new MyException(ErrorCode.ARTICLE_LOCKED);
        }
        threadService.updateArticleViewCount(articleMapper, article);
        return this.copy(article, true, true, true, true);
    }

    @Override
    @Transactional
    @CacheEvict(value = "article", allEntries = true)
    public Long publish(ArticleParams articleParams) {
        // 修改
        if (!StringUtils.isEmpty(articleParams.getId())){
            // 修改文章信息
            Article article = articleMapper.selectById(articleParams.getId());
            // 验证权限
            Long userId = UserThreadLocal.get().getId();
            if (!Objects.equals(userId, article.getAuthorId()) && userId != 1L){
                throw new MyException(ErrorCode.NO_PERMISSION);
            }
            article.setTitle(articleParams.getTitle());
            article.setSummary(articleParams.getSummary());
            article.setCategoryId(articleParams.getCategory().getId());
            articleMapper.updateById(article);
            // 修改文章内容
            ArticleBody articleBody = articleBodyService.getById(article.getBodyId());
            articleBody.setContent(articleParams.getBody().getContent());
            articleBody.setContentBody(articleParams.getBody().getContentHtml());
            articleBodyService.updateById(articleBody);
            // 修改标签（删除旧标签，设置新标签）
            List<TagVo> tags = articleParams.getTags();
            LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ArticleTag::getArticleId, article.getId());
            articleTagService.remove(queryWrapper);
            for (TagVo tag : tags) {
                ArticleTag articleTag = new ArticleTag();
                articleTag.setArticleId(article.getId());
                articleTag.setTagId(tag.getId());
                articleTagService.save(articleTag);
            }
            return articleParams.getId();
        }
        // 新增
        SysUser user = UserThreadLocal.get();
        Article article = new Article();
        article.setCommentCounts(Article.Article_Common);
        article.setWeight(0);
        article.setOnShow(1);
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
        articleBody.setContentBody(articleParams.getBody().getContentHtml());
        articleBodyService.save(articleBody);
        article.setBodyId(articleBody.getId());
        articleMapper.updateById(article);
        return articleId;
    }

    @Override
    public void addCommentCounts(Long articleId) {
        articleMapper.addCommentCounts(articleId);
    }

    @Override
    public Page<ArticleVo> listArticleByUserId(Integer page, Integer size) {
        Page<Article> articlePage = new Page<>(page, size);
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        SysUser user = UserThreadLocal.get();
        queryWrapper.eq("author_id", user.getId());
        queryWrapper.orderByDesc("create_date");
        Page<Article> articles = articleMapper.selectPage(articlePage, queryWrapper);
        List<ArticleVo> articleVoList = this.copyList(articlePage.getRecords(), true, true);
        Page<ArticleVo> articleVoPage = new Page<>(page, size);
        articleVoPage.setRecords(articleVoList);
        articleVoPage.setPages(articles.getPages());
        articleVoPage.setTotal(articles.getTotal());
        articleVoPage.setSize(size);
        return articleVoPage;
    }

    @Override
    @Transactional
    @CacheEvict(value = "article", allEntries = true)
    public void removeArticleById(Long id) {
        SysUser user = UserThreadLocal.get();
        Article article = articleMapper.selectById(id);
        // 判断文章是否存在
        if (article == null) {
            throw new MyException(ErrorCode.ARTICLE_NOT_FOUND);
        }
        // 只有文章作者和超级管理员能删除
        if (!Objects.equals(article.getAuthorId(), user.getId()) && user.getId() != 1) {
            throw new MyException(ErrorCode.NO_PERMISSION);
        }
        // 删除文章body
        LambdaQueryWrapper<ArticleBody> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleBody::getArticleId, id);
        articleBodyService.remove(queryWrapper);
        // 删除文章关联的标签记录
        LambdaQueryWrapper<ArticleTag> articleTagLambdaQueryWrapper = new LambdaQueryWrapper<>();
        articleTagLambdaQueryWrapper.eq(ArticleTag::getArticleId, id);
        articleTagService.remove(articleTagLambdaQueryWrapper);
        // 删除相关评论
        LambdaQueryWrapper<Comment> commentLambdaQueryWrapper = new LambdaQueryWrapper<>();
        commentLambdaQueryWrapper.eq(Comment::getArticleId, id);
        commentsService.remove(commentLambdaQueryWrapper);
        // 删除文章
        articleMapper.deleteById(id);
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
            SysUser user = sysUserService.getById(authorId);
            articleVo.setAuthor(user.getNickname());
            articleVo.setAvatar(user.getAvatar());
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
