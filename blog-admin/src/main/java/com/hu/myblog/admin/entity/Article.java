package com.hu.myblog.admin.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author suhu
 * @createDate 2022/3/7
 */
@Data
public class Article {
    public static final Integer Article_TOP = 1;

    public static final Integer Article_Common = 0;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private String title;

    private String summary;

    private Integer commentCounts;

    private Integer viewCounts;

    /**
     * 作者id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long authorId;
    /**
     * 内容id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long bodyId;
    /**
     *类别id
     */

    private Long categoryId;

    /**
     * 置顶
     */
    private Integer weight;


    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    private Integer published;

    private Integer onShow;

    @TableField(exist = false)
    private Map<String, Object> params = new HashMap<>();

}
