package com.hu.myblog.admin.vo;

import com.hu.myblog.admin.entity.Role;
import lombok.Data;

import java.util.List;

/**
 * @author suhu
 * @createDate 2022/3/12
 */
@Data
public class AdminVo {
    private Long id;

    private String username;

    private List<Role> roles;
}
