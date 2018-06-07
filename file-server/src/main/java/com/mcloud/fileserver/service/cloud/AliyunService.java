package com.mcloud.fileserver.service.cloud;

import com.mcloud.fileserver.repository.entity.ConfAliyun;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AliyunService {
    boolean uploadFile(ConfAliyun confAliyun, String localFilePath);
    String uploadMultipartFile(ConfAliyun confAliyun,MultipartFile multipartFile, String remotePath) throws IOException;
    void downloadFile(ConfAliyun confAliyun, String yunfileName, String saveLocalFilePath);
    void deleteFile(ConfAliyun confAliyun, String fileName);
}
