package com.mcloud.fileserver.service.cloud;


import com.mcloud.fileserver.repository.entity.ConfQcloud;

public interface QcloudService {

    String uploadFile(ConfQcloud confQcloud, String localFilePath);
    String downFile(ConfQcloud confQcloud, String yunfileName, String localPathDown);
    String moveFile(ConfQcloud confQcloud, String cosFilePath, String dstCosFilePath);
    String getFileProp(ConfQcloud confQcloud, String yunFilePath);
    String deleteFile(ConfQcloud confQcloud, String fileName);
}
