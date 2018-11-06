package com.mcloud.fileserver.service.cloud;


import org.apache.commons.lang3.tuple.Pair;
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

    /**
     *
     * @param cloudFilePath 云文件路径
     * @param localFilePath 本地保存路径
     * @return 返回类型为下载的完整文件名路径
     */
    String downLoadFile( String cloudFilePath, String localFilePath);


    boolean deleteFile(String cloudFilePath);


    //断点续传文件，即文件分片上传后在云端合并
    String uploadMultiPartFile(String localFilePath);
}
