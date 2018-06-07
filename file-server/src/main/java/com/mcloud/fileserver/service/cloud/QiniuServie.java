package com.mcloud.fileserver.service.cloud;

import com.mcloud.fileserver.repository.entity.ConfQiniu;

import java.io.IOException;

public interface QiniuServie {
    void uploadFile(ConfQiniu confQiniu, String localFilePath);
    void randomAcessUpLoadFile(ConfQiniu confQiniu, String localFilePath);
    void urlDownLoadSource(ConfQiniu confQiniu, String finalUrl, String fileName, String saveFilePath) throws IOException;
    void downLoadPublicFile(ConfQiniu confQiniu, String fileName, String saveFilePath) throws IOException;
    void downLoadPrivateFile(ConfQiniu confQiniu, String fileName, String saveFilePath) throws IOException;
    void getYunFileInfomation(ConfQiniu confQiniu, String yunFileName);
    void deleteCloudFile(ConfQiniu confQiniu, String fileName);
}
