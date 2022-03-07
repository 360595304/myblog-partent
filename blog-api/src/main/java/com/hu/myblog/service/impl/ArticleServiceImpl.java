package com.hu.myblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hu.myblog.entity.Article;
import com.hu.myblog.mapper.ArticleMapper;
import com.hu.myblog.service.ArticleService;
import com.hu.myblog.service.SysUserService;
import com.hu.myblog.service.TagService;
import com.hu.myblog.vo.ArticleVo;
import com.hu.myblog.vo.params.PageParams;
import org.joda.time.DateTime;
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

    private List<ArticleVo> copyList(List<Article> articleList, boolean isTag, boolean isAuthor){
        List<ArticleVo> articleVoList = new ArrayList<>();
        articleList.forEach(article -> {
            ArticleVo articleVo = new ArticleVo();
            BeanUtils.copyProperties(article, articleVo);
            articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
            if (isTag) {
                articleVo.setTags(tagService.findTagsByArticleId(article.getId()));
            }
            if (isAuthor) {
                Long authorId = article.getAuthorId();
                articleVo.setAuthor(sysUserService.getById(authorId).getNickname());
            }
            articleVoList.add(articleVo);
        });
        return articleVoList;
    }
}
