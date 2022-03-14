package com.hu.myblog.admin.modle.params;

import lombok.Data;

import java.util.Date;

/**
 * @author suhu
 * @createDate 2022/3/12
 */
@Data
public class ArticleParam {
    private Integer currentPage;

    private Integer pageSize;

    private String title;

    private Integer published;

    private Integer show;

    private Date createDate;
}
