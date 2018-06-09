package com.mcloud.fileserver.service.file.Impl;

import com.mcloud.fileserver.service.file.CloudFilePathService;
import org.springframework.stereotype.Service;

/**
 * @Author: vellerzheng
 * @Description:
 * @Date:Created in 17:16 2018/6/8
 * @Modify By:
 */
@Service
public class CloudFilePathServiceImpl implements CloudFilePathService {
    @Override
    public String updateCloudPathToLocal(Object jsonObject) {
        return "断路器断路了";
    }
}
