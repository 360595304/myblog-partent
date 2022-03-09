package com.hu.myblog.controller;

import com.hu.myblog.entity.Comment;
import com.hu.myblog.result.Result;
import com.hu.myblog.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author suhu
 * @createDate 2022/3/9
 */
@RestController
@RequestMapping("/api/comments")
public class CommentsController {

    @Autowired
    private CommentsService commentsService;

    @GetMapping("/article/{id}")
    public Result comments(@PathVariable Long id) {
        List<Comment> commentList = commentsService.getCommentsByArticleId(id);
        return Result.ok(commentList);
    }
}
