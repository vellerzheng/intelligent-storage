package com.mcloud.fileserver.service.provider;

import com.alibaba.fastjson.JSONObject;
import com.mcloud.fileserver.service.User.impl.UserServiceImpl;
import com.mcloud.fileserver.service.provider.Impl.CloudInfoServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: vellerzheng
 * @Description:
 * @Date:Created in 17:15 2018/6/8
 * @Modify By:
 */
@FeignClient(value="storage-web",fallback = CloudInfoServiceImpl.class)
public interface CloudInfoService {
    @RequestMapping(value = "/cloudPath")
    String provideCloudPath(Object Object);
}
