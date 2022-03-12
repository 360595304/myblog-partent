package com.hu.myblog.vo.params;

import lombok.Data;

/**
 * @author suhu
 * @createDate 2022/3/7
 */
@Data
public class PageParams {
    private int page = 1;

    private int pageSize = 5;

    private Long categoryId;

    private Long tagId;

    private String year;

    private String month;
}
