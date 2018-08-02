package com.mcloud.storageweb.service.file.Impl;

import com.mcloud.storageweb.repository.entity.FileHash;
import com.mcloud.storageweb.repository.entity.User;
import com.mcloud.storageweb.repository.entity.common.ConfCloud;
import com.mcloud.storageweb.service.file.SchedualFileService;
import com.mcloud.storageweb.util.InfoJson;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: vellerzheng
 * @Description:
 * @Date:Created in 17:39 2018/8/2
 * @Modify By:
 */
@Component
public class SchedualFileServiceImpl implements SchedualFileService {


    @Override
    public InfoJson downLoadFile(ConfCloud confCloud, Integer userId, String userName, String fileName, String filePath) {
        return InfoJson.getFail("schedule file-server download Api failed.");
    }
}
