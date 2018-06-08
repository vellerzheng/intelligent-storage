package com.mcloud.fileserver.service.designPattern.cloudAbstractFactory;

import com.mcloud.fileserver.repository.entity.ConfNetease;
import com.mcloud.fileserver.service.cloud.CloudService;
import com.mcloud.fileserver.service.cloud.impl.NeteaseServiceImpl;

/**
 * @Author: vellerzheng
 * @Description:
 * @Date:Created in 16:15 2018/6/7
 * @Modify By:
 */

public class NetEaseFactory implements Provider {
    @Override
    public CloudService produce(Object obj) {
        return new NeteaseServiceImpl((ConfNetease) obj);
    }
}
