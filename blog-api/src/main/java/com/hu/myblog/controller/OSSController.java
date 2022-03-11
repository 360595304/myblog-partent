package com.hu.myblog.controller;

import com.hu.myblog.result.ErrorCode;
import com.hu.myblog.result.Result;
import com.hu.myblog.service.OSSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author suhu
 * @createDate 2022/3/11
 */
@RestController
@RequestMapping("/api/oss")
public class OSSController {

    @Autowired
    private OSSService ossService;

    @PostMapping("/auth/upload")
    public Result upload(@RequestParam("image") MultipartFile file) {
        String url = ossService.uploadFile(file);
        if (StringUtils.isEmpty(url)) {
            return Result.fail(ErrorCode.UPLOAD_ERROR);
        }
        return Result.ok(url);
    }
}
