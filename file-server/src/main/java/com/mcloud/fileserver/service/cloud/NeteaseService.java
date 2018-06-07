package com.mcloud.fileserver.service.cloud;


import com.mcloud.fileserver.repository.entity.ConfNetease;

public interface NeteaseService {


    boolean uploadMultiPartFile(ConfNetease confNetease, String localFilePath);
    boolean uploadFile(ConfNetease confNetease,String localFilePath);
    boolean downFile(ConfNetease confNetease, String yunFilePath, String localPathDown);
    boolean deleteFile(ConfNetease confNetease, String yunFilePath);
}
