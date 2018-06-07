package com.mcloud.storageweb.service.file;

import com.mcloud.storageweb.repository.entity.User;

/**
 * @Author: vellerzheng
 * @Description:
 * @Date:Created in 19:36 2018/6/5
 * @Modify By:
 */
public interface FileService {
    boolean tranferEventToFileServer(User user, String filePath);
}
