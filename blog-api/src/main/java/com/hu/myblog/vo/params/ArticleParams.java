package com.hu.myblog.vo.params;

import com.hu.myblog.vo.ArticleBodyVo;
import com.hu.myblog.vo.CategoryVo;
import com.hu.myblog.vo.TagVo;
import lombok.Data;

import java.util.List;

/**
 * @author suhu
 * @createDate 2022/3/10
 */
@Data
public class ArticleParams {
    private Long id;

    private String title;

    private String summary;

    private ArticleBodyParams body;

    private List<TagVo> tags;

    private CategoryVo category;
}
