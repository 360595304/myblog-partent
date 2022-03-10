package com.hu.myblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hu.myblog.entity.Tag;
import com.hu.myblog.vo.TagVo;

import java.util.List;

public interface TagService extends IService<Tag> {
    List<TagVo> findTagsByArticleId(Long id);

    List<Tag> hotTag(int limit);

    List<TagVo> findAllTagVo();
}
