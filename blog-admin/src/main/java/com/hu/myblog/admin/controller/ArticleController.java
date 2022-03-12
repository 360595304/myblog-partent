package com.hu.myblog.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hu.myblog.admin.entity.Article;
import com.hu.myblog.admin.entity.SysUser;
import com.hu.myblog.admin.modle.params.ArticleParam;
import com.hu.myblog.admin.modle.params.PageParam;
import com.hu.myblog.admin.result.Result;
import com.hu.myblog.admin.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
