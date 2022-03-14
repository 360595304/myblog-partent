package com.hu.myblog.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hu.myblog.admin.entity.Category;
import com.hu.myblog.admin.modle.params.PageParam;
import com.hu.myblog.admin.result.ErrorCode;
import com.hu.myblog.admin.result.Result;
import com.hu.myblog.admin.service.CategoryService;
import com.hu.myblog.admin.service.OSSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author suhu
 * @createDate 2022/3/14
 */
@RestController
@RequestMapping("/admin/category")
public class CategoryAdminController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private OSSService ossService;

    @PostMapping("/categoryList")
    public Result categoryList(@RequestBody PageParam pageParam) {
        Page<Category> categoryPage = categoryService.getCategoryPage(pageParam);
        return Result.ok(categoryPage);
    }

    @PostMapping("/add")
    public Result categoryAdd(@RequestBody Category category) {
        categoryService.save(category);
        return Result.ok();
    }

    @PostMapping("/update")
    public Result categoryUpdate(@RequestBody Category category) {
        categoryService.updateById(category);
        return Result.ok();
    }

    @GetMapping("/delete/{id}")
    public Result categoryUpdate(@PathVariable Long id) {
        categoryService.removeById(id);
        return Result.ok();
    }

    @PostMapping("/upload")
    public Result avatarUpload(MultipartFile file) {
        String path = ossService.uploadFile(file, "category");
        if (StringUtils.isEmpty(path)) {
            return Result.fail(ErrorCode.UPLOAD_ERROR);
        }
        return Result.ok(path);
    }

}
