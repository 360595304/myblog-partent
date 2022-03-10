package com.hu.myblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hu.myblog.entity.Comment;
import com.hu.myblog.entity.SysUser;
import com.hu.myblog.mapper.CommentsMapper;
import com.hu.myblog.service.ArticleService;
import com.hu.myblog.service.CommentsService;
import com.hu.myblog.service.SysUserService;
import com.hu.myblog.utils.UserThreadLocal;
import com.hu.myblog.vo.CommentVo;
import com.hu.myblog.vo.SimpleUser;
import com.hu.myblog.vo.params.CommentParams;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
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

    @Autowired
    private ArticleService articleService;

    @Override
    public List<CommentVo> getCommentsByArticleId(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getArticleId, id);
        queryWrapper.eq(Comment::getLevel, 1);
        queryWrapper.orderByAsc(Comment::getCreateDate);
        List<Comment> commentList = commentsMapper.selectList(queryWrapper);
        return this.copyList(commentList);
    }

    private List<CommentVo> copyList(List<Comment> commentList) {
        List<CommentVo> commentVoList = new ArrayList<>();
        commentList.forEach(comment -> {
            CommentVo commentVo = this.pack(comment);
            commentVoList.add(commentVo);
        });
        return commentVoList;
    }

    @Override
    public List<Comment> getChildrenByParentId(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getParentId, id);
        queryWrapper.eq(Comment::getLevel, 2);
        queryWrapper.orderByAsc(Comment::getCreateDate);
        return commentsMapper.selectList(queryWrapper);
    }

    @Override
    public void saveComment(CommentParams commentParams) {
        SysUser user = UserThreadLocal.get();
        Comment comment = new Comment();
        comment.setContent(commentParams.getContent());
        Long articleId = commentParams.getArticleId();
        comment.setArticleId(articleId);
        comment.setAuthorId(user.getId());
        comment.setCreateDate(new Date());
        Long parent = commentParams.getParent();
        if (parent == null || parent == 0) {
            comment.setLevel(1);
        } else {
            comment.setLevel(2);
        }
        comment.setParentId(parent == null ? 0 : parent);
        Long toUserId = commentParams.getToUserId();
        comment.setToUid(toUserId == null ? 0 : toUserId);
        articleService.addCommentCounts(articleId);
        commentsMapper.insert(comment);
    }

    private CommentVo pack(Comment comment) {
        CommentVo commentVo = new CommentVo();
        BeanUtils.copyProperties(comment, commentVo);
        Long authorId = comment.getAuthorId();
        SysUser user = userService.getById(authorId);
        SimpleUser simpleUser = new SimpleUser();
        BeanUtils.copyProperties(user, simpleUser);
        commentVo.setAuthor(simpleUser);
        if (comment.getLevel() == 1) {
            List<Comment> children = this.getChildrenByParentId(comment.getId());
            commentVo.setChildren(this.copyList(children));
        }
        if (comment.getLevel() > 1) {
            SysUser toUser = userService.getById(comment.getToUid());
            SimpleUser simpleUser1 = new SimpleUser();
            BeanUtils.copyProperties(toUser, simpleUser1);
            commentVo.setToUser(simpleUser1);
        }
        return commentVo;
    }
}
