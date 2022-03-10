package com.hu.myblog.controller;

import com.hu.myblog.entity.Comment;
import com.hu.myblog.entity.SysUser;
import com.hu.myblog.result.Result;
import com.hu.myblog.service.CommentsService;
import com.hu.myblog.utils.UserThreadLocal;
import com.hu.myblog.vo.CommentVo;
import com.hu.myblog.vo.params.CommentParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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
        List<CommentVo> commentList = commentsService.getCommentsByArticleId(id);
        return Result.ok(commentList);
    }

    @PostMapping("/auth/create/change")
    public Result comment(@RequestBody CommentParams commentParams) {
        commentsService.saveComment(commentParams);

        return Result.ok();
    }
}
