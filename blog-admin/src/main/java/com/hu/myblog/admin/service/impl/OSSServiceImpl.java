package com.hu.myblog.admin.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.hu.myblog.admin.service.OSSService;
import com.hu.myblog.admin.utils.ConstantOssPropertiesUtil;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @author suhu
 * @createDate 2022/3/11
 */
@Service
public class OSSServiceImpl implements OSSService {
    @Override
    public String uploadFile(MultipartFile file, String path) {
        // Endpoint以Region请按实际情况填写。
        String endpoint = ConstantOssPropertiesUtil.ENDPOINT;
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = ConstantOssPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = ConstantOssPropertiesUtil.SECRET;
        // 填写Bucket名称
        String bucketName = ConstantOssPropertiesUtil.BUCKET;

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            InputStream inputStream = file.getInputStream();
            String fileName = StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            fileName = path + "/" + uuid + "." + fileName;
            // 创建PutObject请求。
            ossClient.putObject(bucketName, fileName, inputStream);
            return "http://static.yyzblog.top/" + fileName;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return null;
    }
}