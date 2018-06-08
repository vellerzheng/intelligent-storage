package com.mcloud.fileserver.service.designPattern.cloudAbstractFactory;

import com.mcloud.fileserver.repository.entity.ConfQiniu;
import com.mcloud.fileserver.service.cloud.CloudService;
import com.mcloud.fileserver.service.cloud.impl.QiniuServiceImpl;

/**
 * @Author: vellerzheng
 * @Description:
 * @Date:Created in 16:18 2018/6/7
 * @Modify By:
 */

public class QiniuFactory implements Provider {
    @Override
    public CloudService produce(Object obj) {
        return new QiniuServiceImpl((ConfQiniu)obj);
    }

}
