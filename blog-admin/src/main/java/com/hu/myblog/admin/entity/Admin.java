package com.hu.myblog.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author suhu
 * @createDate 2022/3/12
 */
@Data
public class Admin {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String username;

    private String password;
}
