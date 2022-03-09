package com.hu.myblog.vo;

import lombok.Data;
import org.springframework.web.servlet.view.script.ScriptTemplateConfig;

/**
 * @author suhu
 * @createDate 2022/3/8
 */
@Data
public class ArchiveVo {
    private String year;

    private String month;

    private Integer count;
}
