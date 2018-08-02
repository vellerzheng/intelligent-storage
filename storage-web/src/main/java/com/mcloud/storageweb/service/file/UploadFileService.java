package com.mcloud.storageweb.service.file;

import com.mcloud.storageweb.repository.entity.common.FileInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: vellerzheng
 * @Description:
 * @Date:Created in 15:12 2018/8/2
 * @Modify By:
 */
public interface UploadFileService {
    FileInfo uploadFile(MultipartFile multipartFile);
}
