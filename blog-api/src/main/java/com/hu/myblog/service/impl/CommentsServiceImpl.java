package com.hu.myblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hu.myblog.entity.Comment;
import com.hu.myblog.entity.SysUser;
import com.hu.myblog.mapper.CommentsMapper;
import com.hu.myblog.service.CommentsService;
import com.hu.myblog.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author suhu
 * @createDate 2022/3/9
 */
@Service
public class CommentsServiceImpl extends ServiceImpl<CommentsMapper, Comment> implements CommentsService{

    @Autowired
    private CommentsMapper commentsMapper;

    @Autowired
    private SysUserService userService;


    @Override
    public List<Comment> getCommentsByArticleId(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getArticleId, id);
        queryWrapper.eq(Comment::getLevel, 1);
        List<Comment> commentList = commentsMapper.selectList(queryWrapper);
        commentList.forEach(this::pack);
        return commentList;
    }

    @Override
    public List<Comment> getChildrenByParentId(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getParentId, id);
        queryWrapper.orderByAsc(Comment::getCreateDate);
        return commentsMapper.selectList(queryWrapper);
    }

    private void pack(Comment comment) {
        Long authorId = comment.getAuthorId();
        SysUser user = userService.getById(authorId);
        comment.getParams().put("author", user.getNickname());
        comment.getParams().put("avatar", user.getAvatar());
        if (comment.getLevel() == 1) {
            List<Comment> children = this.getChildrenByParentId(comment.getId());
            comment.getParams().put("children", children);
        }
    }
}
