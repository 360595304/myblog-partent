package com.hu.myblog.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hu.myblog.admin.entity.Article;
import com.hu.myblog.admin.entity.Category;
import com.hu.myblog.admin.entity.Tag;
import com.hu.myblog.admin.modle.params.ArticleParam;
import com.hu.myblog.admin.modle.params.PageParam;
import com.hu.myblog.admin.result.ErrorCode;
import com.hu.myblog.admin.result.Result;
import com.hu.myblog.admin.service.ArticleService;
import com.hu.myblog.admin.service.OSSService;
import com.hu.myblog.admin.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author suhu
 * @createDate 2022/3/14
 */
@RestController
@RequestMapping("/admin/tag")
public class TagAdminController {

    @Autowired
    private TagService tagService;

    @Autowired
    private OSSService ossService;

    @PostMapping("/tagList")
    public Result tagList(@RequestBody PageParam pageParam) {
        Page<Tag> tagPage = tagService.getTagPage(pageParam);
        return Result.ok(tagPage);
    }

    @PostMapping("/add")
    public Result tagAdd(@RequestBody Tag tag) {
        tagService.save(tag);
        return Result.ok();
    }

    @PostMapping("/update")
    public Result tagUpdate(@RequestBody Tag tag) {
        tagService.updateById(tag);
        return Result.ok();
    }

    @GetMapping("/delete/{id}")
    public Result tagUpdate(@PathVariable Long id) {
        tagService.removeById(id);
        return Result.ok();
    }

    @PostMapping("/upload")
    public Result avatarUpload(MultipartFile file) {
        String path = ossService.uploadFile(file, "tag");
        if (StringUtils.isEmpty(path)) {
            return Result.fail(ErrorCode.UPLOAD_ERROR);
        }
        return Result.ok(path);
    }

}
