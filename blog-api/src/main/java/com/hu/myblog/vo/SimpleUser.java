package com.hu.myblog.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;


/**
 * @author suhu
 * @createDate 2022/3/9
 */
@Data
public class SimpleUser {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private String avatar;

    private String nickname;
}
