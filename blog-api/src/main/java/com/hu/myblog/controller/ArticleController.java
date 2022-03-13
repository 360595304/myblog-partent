package com.hu.myblog.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hu.myblog.common.aop.LogAnnotation;
import com.hu.myblog.result.Result;
import com.hu.myblog.service.ArticleService;
import com.hu.myblog.vo.ArchiveVo;
import com.hu.myblog.vo.ArticleVo;
import com.hu.myblog.vo.params.ArticleParams;
import com.hu.myblog.vo.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author suhu
 * @createDate 2022/3/7
 */
@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    //文章列表
    @PostMapping("")
    @LogAnnotation(module = "文章", operator = "获取文章列表")
    public Result listArticle(@RequestBody PageParams pageParams) {
        Page<ArticleVo> articleList = articleService.listArticle(pageParams);
        return Result.ok(articleList);
    }

    // 最热文章
    @GetMapping("/hot")
    public Result hotArticles() {
        int limit = 5;
        List<ArticleVo> articleList = articleService.hotArticles(limit);
        return Result.ok(articleList);
    }

    // 最新文章
    @GetMapping("/new")
    public Result newArticles() {
        int limit = 5;
        List<ArticleVo> articleList = articleService.newArticles(limit);
        return Result.ok(articleList);
    }


    // 按年月归档
    @GetMapping("/listArchives")
    public Result listArchives() {
        List<ArchiveVo> articleList = articleService.listArchives();
        return Result.ok(articleList);
    }

    // 根据id查询文章
    @GetMapping("/view/{id}")
    public Result findArticleById(@PathVariable long id) {
        ArticleVo articleVo = articleService.findArticleVoById(id);
        return Result.ok(articleVo);
    }

    // 发布文章
    @PostMapping("/auth/publish")
    public Result publishArticle(@RequestBody ArticleParams articleParams) {
        Long id = articleService.publish(articleParams);
        Map<String, Object> result = new HashMap<String, Object>(){{
            put("id", id.toString());
        }};
        return Result.ok(result);
    }

    // 用户文章
    @GetMapping("/auth/userArticle/{page}/{size}")
    public Result userArticle(@PathVariable Integer page,
                              @PathVariable Integer size) {
        Page<ArticleVo> articleList = articleService.listArticleByUserId(page, size);
        return Result.ok(articleList);
    }

    @GetMapping("/{id}")
    public Result getArticleById(@PathVariable Long id) {
        ArticleVo articleVo = articleService.findArticleVoById(id);
        return Result.ok(articleVo);
    }

    @PostMapping("/auth/delete/{id}")
    public Result deleteArticle(@PathVariable Long id) {
        articleService.removeArticleById(id);
        return Result.ok();
    }

}
