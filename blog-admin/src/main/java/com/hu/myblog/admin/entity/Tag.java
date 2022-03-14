package com.hu.myblog.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author suhu
 * @createDate 2022/3/7
 */
@Data
public class Tag {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String avatar;

    private String tagName;
}
