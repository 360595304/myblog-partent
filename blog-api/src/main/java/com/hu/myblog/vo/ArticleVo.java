package com.hu.myblog.vo;

import lombok.Data;

import java.util.List;

/**
 * @author suhu
 * @createDate 2022/3/7
 */
@Data
public class ArticleVo {
    private Long id;

    private String title;

    private String summary;

    private int commentCounts;

    private int viewCounts;

    private int weight;
    /**
     * 创建时间
     */
    private String createDate;

    private String author;

//    private ArticleBodyVo body;
//
    private List<TagVo> tags;
//
//    private List<CategoryVo> categorys;
}
