package com.mcloud.fileserver.service.file.fileOperate.download;


import com.mcloud.fileserver.repository.entity.ConfCloud;
import com.mcloud.fileserver.repository.entity.FileEntity;
import com.mcloud.fileserver.repository.entity.FileHash;
import com.mcloud.fileserver.repository.entity.User;

/**
 * @Author: vellerzheng
 * @Description:
 * @Date:Created in 15:41 2018/6/6
 * @Modify By:
 */
public interface DownLoadService {

    void downLoadFile();
    FileEntity downLoadFile(ConfCloud confCloud, Integer userId, String userName,  String realFileName, String localFilePath);
}
