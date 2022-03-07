package com.hu.myblog.vo.params;

import lombok.Data;

/**
 * @author suhu
 * @createDate 2022/3/7
 */
@Data
public class PageParams {
    private int page = 1;

    private int pageSize = 10;
}
