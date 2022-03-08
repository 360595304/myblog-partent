package com.hu.myblog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hu.myblog.entity.Article;
import com.hu.myblog.vo.ArchiveVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author suhu
 * @createDate 2022/3/7
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
    List<ArchiveVo> listArchives();
}
