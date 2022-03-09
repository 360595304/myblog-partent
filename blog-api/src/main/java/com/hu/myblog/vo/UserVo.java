package com.hu.myblog.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author suhu
 * @createDate 2022/3/9
 */
@Data
public class UserVo {
    private Long id;

    private String account;

    private String avatar;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    private String email;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastLogin;

    private String mobilePhoneNumber;

    private String nickname;

}
