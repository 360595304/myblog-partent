package com.hu.myblog.admin.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author suhu
 * @createDate 2022/3/9
 */
@Data
public class ArticleBody {
    @TableId
    private Long id;
    private Long articleId;
    private String content;

    @TableField(value = "content_html")
    private String contentBody;

}
