package com.hu.myblog.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hu.myblog.entity.Article;
import com.hu.myblog.result.Result;
import com.hu.myblog.service.ArticleService;
import com.hu.myblog.vo.ArchiveVo;
import com.hu.myblog.vo.ArticleVo;
import com.hu.myblog.vo.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.util.List;


/**
 * @author suhu
 * @createDate 2022/3/7
 */
@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping("/")
    public Result listArticle(@RequestBody PageParams pageParams) {
        Page<ArticleVo> articleList = articleService.listArticle(pageParams);
        return Result.ok(articleList);
    }

    @GetMapping("/hot")
    public Result hotArticles() {
        int limit = 5;
        List<ArticleVo> articleList = articleService.hotArticles(limit);
        return Result.ok(articleList);
    }

    @GetMapping("/new")
    public Result newArticles() {
        int limit = 5;
        List<ArticleVo> articleList = articleService.newArticles(limit);
        return Result.ok(articleList);
    }

    @GetMapping("/archives")
    public Result listArchives() {
        List<ArchiveVo> articleList = articleService.listArchives();
        return Result.ok(articleList);
    }

}
