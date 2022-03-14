package com.hu.myblog.admin.controller;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hu.myblog.admin.entity.Article;
import com.hu.myblog.admin.modle.params.ArticleParam;
import com.hu.myblog.admin.result.Result;
import com.hu.myblog.admin.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * @author suhu
 * @createDate 2022/3/12
 */
@RestController
@RequestMapping("/admin/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @PostMapping("/articleList")
    public Result articleList(@RequestBody ArticleParam articleParam) {
        Page<Article> articlePage = articleService.getArticlePage(articleParam);
        return Result.ok(articlePage);
    }

    @GetMapping("/weight/{id}/{weight}")
    public Result changeWeight(@PathVariable Long id,
                               @PathVariable Integer weight) {
        LambdaUpdateWrapper<Article> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Article::getId, id).set(Article::getWeight, weight);
        articleService.update(updateWrapper);
        return Result.ok();
    }

    @GetMapping("/onShow/{id}/{onShow}")
    public Result changeOnShow(@PathVariable Long id,
                               @PathVariable Integer onShow) {
        LambdaUpdateWrapper<Article> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Article::getId, id).set(Article::getOnShow, onShow);
        articleService.update(updateWrapper);
        return Result.ok();
    }
}
