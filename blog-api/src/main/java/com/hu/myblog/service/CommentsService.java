package com.hu.myblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hu.myblog.entity.Comment;
import com.hu.myblog.vo.CommentVo;

import java.util.List;

public interface CommentsService extends IService<Comment> {
    List<CommentVo> getCommentsByArticleId(Long id);

    List<Comment> getChildrenByParentId(Long id);
}
