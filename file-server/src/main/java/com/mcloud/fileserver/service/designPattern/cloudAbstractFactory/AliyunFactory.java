package com.mcloud.fileserver.service.designPattern.cloudAbstractFactory;

import com.mcloud.fileserver.repository.entity.ConfAliyun;
import com.mcloud.fileserver.service.cloud.CloudService;
import com.mcloud.fileserver.service.cloud.impl.AliyunServiceImpl;

/**
 * @Author: vellerzheng
 * @Description:
 * @Date:Created in 16:09 2018/6/7
 * @Modify By:
 */

public class AliyunFactory implements Provider {
    @Override
    public CloudService produce(Object obj) {
        return new AliyunServiceImpl((ConfAliyun) obj);
    }
}
