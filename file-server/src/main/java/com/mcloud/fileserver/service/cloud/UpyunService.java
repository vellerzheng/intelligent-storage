package com.mcloud.fileserver.service.cloud;

import com.mcloud.fileserver.repository.entity.ConfUpyun;

public interface UpyunService {
    boolean uploadFile(ConfUpyun confUpyun, String localFilePath);
    boolean deleteYunFile(ConfUpyun confUpyun, String fileName);
    boolean downloadFile(ConfUpyun confUpyun, String fileName, String saveFilePath);
    void getFileInformation(ConfUpyun confUpyun, String fileName);
    void getSpaceCapacity(ConfUpyun confUpyun);

}
