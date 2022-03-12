package com.hu.myblog.admin.modle.params;

import lombok.Data;

/**
 * @author suhu
 * @createDate 2022/3/12
 */
@Data
public class PageParam {
    private Integer currentPage;

    private Integer pageSize;

    private String queryString;
}
