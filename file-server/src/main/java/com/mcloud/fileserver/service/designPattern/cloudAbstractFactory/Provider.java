package com.mcloud.fileserver.service.designPattern.cloudAbstractFactory;

import com.mcloud.fileserver.service.cloud.CloudService;

/**
 * @Author: vellerzheng
 * @Description:
 * @Date:Created in 16:10 2018/6/7
 * @Modify By:
 */
public interface Provider {
    public CloudService produce(Object obj);
}
