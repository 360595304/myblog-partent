package com.hu.myblog.controller;

import com.hu.myblog.entity.Tag;
import com.hu.myblog.result.Result;
import com.hu.myblog.service.TagService;
import com.hu.myblog.vo.TagVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author suhu
 * @createDate 2022/3/8
 */
@RestController
@RequestMapping("/api/tags")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/hot")
    public Result hotTag() {
        int limit = 6;
        List<Tag> tagList = tagService.hotTag(limit);

        return Result.ok(tagList);
    }
}
