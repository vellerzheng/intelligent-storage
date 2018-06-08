package com.mcloud.fileserver.service.provider.Impl;

import com.alibaba.fastjson.JSONObject;
import com.mcloud.fileserver.service.provider.CloudInfoService;
import org.springframework.stereotype.Service;

/**
 * @Author: vellerzheng
 * @Description:
 * @Date:Created in 17:16 2018/6/8
 * @Modify By:
 */
@Service
public class CloudInfoServiceImpl implements CloudInfoService {
    @Override
    public String provideCloudPath(JSONObject jsonObject) {
        return "断路器断路了";
    }
}
