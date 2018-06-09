package com.mcloud.fileserver.service.file;

import com.mcloud.fileserver.service.file.Impl.CloudFilePathServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: vellerzheng
 * @Description:
 * @Date:Created in 17:15 2018/6/8
 * @Modify By:
 */
@FeignClient(value="storage-web",fallback = CloudFilePathServiceImpl.class)
public interface CloudFilePathService {
    @RequestMapping(value = "/v1/cloudPath")
    String updateCloudPathToLocal(Object Object);
}
