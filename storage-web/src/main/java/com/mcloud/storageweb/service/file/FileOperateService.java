package com.mcloud.storageweb.service.file;

import com.mcloud.storageweb.repository.entity.User;
import com.mcloud.storageweb.repository.entity.common.ConfCloud;

/**
 * @Author: vellerzheng
 * @Description:
 * @Date:Created in 11:04 2018/6/11
 * @Modify By:
 */
public interface FileOperateService {
    boolean uploadFile(User user, Integer fileId, String filePath);

    boolean downLoadFile(User user, Integer fileId, String filePath);

    boolean deleteFile(User user, Integer fileId, String  filePath);

    ConfCloud prepareCloudConfig(Integer userId, Integer fileId);
}
