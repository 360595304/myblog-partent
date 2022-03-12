package com.hu.myblog.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hu.myblog.admin.entity.Tag;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TagMapper extends BaseMapper<Tag> {
    List<Tag> findTagsByArticleId(Long id);

}
