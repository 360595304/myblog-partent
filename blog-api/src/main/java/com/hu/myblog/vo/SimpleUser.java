package com.hu.myblog.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author suhu
 * @createDate 2022/3/9
 */
@Data
public class SimpleUser {
    private Long id;

    private String avatar;

    private String nickname;
}
