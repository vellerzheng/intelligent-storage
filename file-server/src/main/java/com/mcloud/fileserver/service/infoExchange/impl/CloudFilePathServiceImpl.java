package com.mcloud.fileserver.service.infoExchange.impl;

import com.mcloud.fileserver.repository.entity.FileHash;
import com.mcloud.fileserver.service.infoExchange.CloudFilePathService;
import org.springframework.stereotype.Component;

/**
 * @Author: vellerzheng
 * @Description:
 * @Date:Created in 17:16 2018/6/8
 * @Modify By:
 */
@Component
public class CloudFilePathServiceImpl implements CloudFilePathService {
    @Override
    public String updateCloudPathToLocal(FileHash fileHash) {
        return "断路器断路了";
    }
}
