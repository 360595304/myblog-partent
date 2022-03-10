package com.hu.myblog.vo.params;

import lombok.Data;

/**
 * @author suhu
 * @createDate 2022/3/10
 */
@Data
public class CommentParams {
    private Long articleId;

    private String content;

    private Long toUserId;

    private Long parent;

}
