package com.hu.myblog.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.hu.myblog.entity.Article;
import com.hu.myblog.mapper.ArticleMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author suhu
 * @createDate 2022/3/9
 */
@Component
public class ThreadService {


    @Async("taskExecutor")
    public void updateArticleViewCount(ArticleMapper articleMapper, Article article) {
        int viewCounts = article.getViewCounts();
        Article updateArticle = new Article();
        updateArticle.setViewCounts(viewCounts + 1);
        LambdaUpdateWrapper<Article> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Article::getId, article.getId())
                .eq(Article::getViewCounts, viewCounts); // 防止其他线程修改，保证线程安全
        articleMapper.update(updateArticle, updateWrapper);
    }
}
