package com.hu.myblog.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author suhu
 * @createDate 2022/3/9
 */
@Data
public class Category {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String avatar;
    private String categoryName;
    private String description;
}
