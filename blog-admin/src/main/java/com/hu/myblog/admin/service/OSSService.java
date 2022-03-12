package com.hu.myblog.admin.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author suhu
 * @createDate 2022/3/11
 */
public interface OSSService {
    String uploadFile(MultipartFile file);
}
