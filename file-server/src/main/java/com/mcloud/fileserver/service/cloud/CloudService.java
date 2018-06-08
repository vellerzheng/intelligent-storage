package com.mcloud.fileserver.service.cloud;

import javafx.util.Pair;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @Author: vellerzheng
 * @Description:
 * @Date:Created in 14:20 2018/6/7
 * @Modify By:
 */
public interface  CloudService {

    //k 表示所属云服务， v表示上传后文件的云路径
    Pair<String,String> uploadFile(String localFilePath);

    boolean downLoadFile( String cloudFilePath, String localFilePath);

    boolean deleteFile(String cloudFilePath);

    //断点续传文件，即文件分片上传后在云端合并
    String uploadMultiPartFile(String localFilePath);
}
