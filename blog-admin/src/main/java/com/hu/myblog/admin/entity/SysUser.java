package com.hu.myblog.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author suhu
 * @createDate 2022/3/7
 */
@Data
public class SysUser {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String account;

    private Integer admin;

    private String avatar;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    @TableLogic
    private Integer deleted;

    private String email;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastLogin;

    private String mobilePhoneNumber;

    private String nickname;

    private String password;

    private String salt;

    private String status;

    @TableField(exist = false)
    private Map<String, Object> params = new HashMap<>();
}
