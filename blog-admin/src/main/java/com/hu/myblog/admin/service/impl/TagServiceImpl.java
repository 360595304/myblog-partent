package com.hu.myblog.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hu.myblog.admin.entity.Tag;
import com.hu.myblog.admin.mapper.TagMapper;
import com.hu.myblog.admin.modle.params.PageParam;
import com.hu.myblog.admin.service.TagService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author suhu
 * @createDate 2022/3/7
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {
    @Autowired
    private TagMapper tagMapper;


    @Override
    public List<Tag> findTagsByArticleId(Long id) {
        List<Tag> tagList = tagMapper.findTagsByArticleId(id);
        return tagList;
    }

    @Override
    public Page<Tag> getTagPage(PageParam pageParam) {
        Page<Tag> page = new Page<>(pageParam.getCurrentPage(), pageParam.getPageSize());
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(pageParam.getQueryString())) {
            queryWrapper.like(Tag::getTagName, pageParam.getQueryString());
        }
        Page<Tag> tagPage = tagMapper.selectPage(page, queryWrapper);
        return tagPage;
    }

}
