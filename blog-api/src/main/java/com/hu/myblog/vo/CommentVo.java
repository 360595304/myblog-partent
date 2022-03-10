package com.hu.myblog.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author suhu
 * @createDate 2022/3/9
 */
@Data
public class CommentVo {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private SimpleUser author;

    private String content;

    private List<CommentVo> children;

    private Date createDate;

    private SimpleUser toUser;

    private Integer level;
}
