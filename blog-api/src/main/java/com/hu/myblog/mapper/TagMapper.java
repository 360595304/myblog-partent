package com.hu.myblog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hu.myblog.entity.Tag;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TagMapper extends BaseMapper<Tag> {
    List<Tag> findTagsByArticleId(Long id);

    List<Long> hotTag(int limit);
}
