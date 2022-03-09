package com.hu.myblog.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
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

    private Integer commentCounts;

    private Integer viewCounts;

    private Integer weight;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    private String author;

    private ArticleBodyVo body;
//
    private List<TagVo> tags;
//
    private CategoryVo category;
}
