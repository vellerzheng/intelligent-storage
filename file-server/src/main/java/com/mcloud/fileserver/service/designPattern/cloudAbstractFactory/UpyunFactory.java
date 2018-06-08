package com.mcloud.fileserver.service.designPattern.cloudAbstractFactory;

import com.mcloud.fileserver.repository.entity.ConfUpyun;
import com.mcloud.fileserver.service.cloud.CloudService;
import com.mcloud.fileserver.service.cloud.impl.UpyunServiceImpl;

/**
 * @Author: vellerzheng
 * @Description:
 * @Date:Created in 16:20 2018/6/7
 * @Modify By:
 */

public class UpyunFactory implements Provider {
    @Override
    public CloudService produce(Object obj) {
        return new UpyunServiceImpl((ConfUpyun) obj);
    }
}
