package com.hu.myblog.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author suhu
 * @createDate 2022/3/7
 */
@Data
public class ArticleVo {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private String title;

    private String avatar;

    private String summary;

    private Integer commentCounts;

    private Integer viewCounts;

    private Integer weight;
    /**
     * 创建时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm")
    private Date createDate;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long authorId;

    private String author;

    private ArticleBodyVo body;
//
    private List<TagVo> tags;
//
    private CategoryVo category;
}
