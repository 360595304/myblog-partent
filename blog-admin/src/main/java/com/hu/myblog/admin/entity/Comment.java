package com.hu.myblog.admin.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

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

    private Long articleId;

    private Long authorId;

    private Long parentId;

    private Long toUid;

    private Integer level;

}
