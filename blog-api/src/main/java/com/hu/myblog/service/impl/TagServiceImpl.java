package com.hu.myblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.hu.myblog.entity.Tag;
import com.hu.myblog.mapper.TagMapper;
import com.hu.myblog.service.TagService;
import com.hu.myblog.vo.TagVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<TagVo> findTagsByArticleId(Long id) {
        List<Tag> tagList = tagMapper.findTagsByArticleId(id);
        return copyList(tagList);
    }

    @Override
    public List<Tag> hotTag(int limit) {
        List<Long> tagIds = tagMapper.hotTag(limit);
        return tagMapper.selectBatchIds(tagIds);
    }

    @Override
    public List<TagVo> findAllTagVo() {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Tag::getId, Tag::getTagName);
        List<Tag> tagList = tagMapper.selectList(queryWrapper);
        return copyList(tagList);
    }

    @Override
    public List<TagVo> findAllTagVoDetail() {
        List<Tag> tagList = tagMapper.selectList(null);
        return copyList(tagList);
    }

    @Override
    public TagVo getDetailById(Long id) {
        Tag tag = tagMapper.selectById(id);
        TagVo tagVo = new TagVo();
        BeanUtils.copyProperties(tag, tagVo);
        return tagVo;
    }

    private List<TagVo> copyList(List<Tag> tagList) {
        List<TagVo> tagVoList = new ArrayList<>();
        tagList.forEach(tag -> {
            TagVo tagVo = new TagVo();
            BeanUtils.copyProperties(tag, tagVo);
            tagVoList.add(tagVo);
        });
        return tagVoList;
    }
}
