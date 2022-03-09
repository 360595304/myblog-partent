package com.hu.myblog.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author suhu
 * @createDate 2022/3/9
 */
@Data
public class Comment {
    @TableId
    private Long id;
    private String content;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date createDate;

    private Integer articleId;

    private Long authorId;

    private Long parentId;

    private Long toUid;

    private Integer level;

}
