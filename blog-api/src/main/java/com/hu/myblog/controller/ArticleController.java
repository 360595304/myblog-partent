package com.hu.myblog.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hu.myblog.entity.Article;
import com.hu.myblog.result.Result;
import com.hu.myblog.service.ArticleService;
import com.hu.myblog.vo.ArticleVo;
import com.hu.myblog.vo.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;


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
}
