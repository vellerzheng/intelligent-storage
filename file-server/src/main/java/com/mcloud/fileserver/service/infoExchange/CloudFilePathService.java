package com.mcloud.fileserver.service.infoExchange;

import com.mcloud.fileserver.repository.entity.FileHash;
import com.mcloud.fileserver.service.infoExchange.impl.CloudFilePathServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: vellerzheng
 * @Description:
 * @Date:Created in 17:15 2018/6/8
 * @Modify By:
 */
@FeignClient(value="storage-web",fallback = CloudFilePathServiceImpl.class)
public interface CloudFilePathService {
    @RequestMapping(value = "/api/v1/cloudPath")
    String insertCloudPath(@RequestBody FileHash fileHash);
}
