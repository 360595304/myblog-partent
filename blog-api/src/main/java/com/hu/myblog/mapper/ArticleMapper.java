package com.hu.myblog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hu.myblog.entity.Article;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author suhu
 * @createDate 2022/3/7
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
}
