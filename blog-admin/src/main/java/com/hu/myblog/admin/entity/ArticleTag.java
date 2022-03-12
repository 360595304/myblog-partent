package com.hu.myblog.admin.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author suhu
 * @createDate 2022/3/10
 */
@Data
public class ArticleTag {
    @TableId
    private Long id;

    private Long articleId;

    private Long tagId;
}
