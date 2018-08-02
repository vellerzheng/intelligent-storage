package com.mcloud.storageweb.service.file;

import com.mcloud.storageweb.repository.entity.FileHash;
import com.mcloud.storageweb.repository.entity.User;
import com.mcloud.storageweb.repository.entity.common.ConfCloud;
import com.mcloud.storageweb.service.file.Impl.SchedualFileServiceImpl;
import com.mcloud.storageweb.util.InfoJson;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: vellerzheng
 * @Description:
 * @Date:Created in 17:37 2018/8/2
 * @Modify By:
 */
@FeignClient(value="file-server",fallback = SchedualFileServiceImpl.class)
public interface SchedualFileService {
    @RequestMapping(value = "/api/v1/downloadFile")
    InfoJson downLoadFile(@RequestBody ConfCloud confCloud, @RequestParam("userId")Integer userId,
                          @RequestParam("username") String userName, @RequestParam("fileName")String fileName,
                          @RequestParam("filePath")String filePath);


}
