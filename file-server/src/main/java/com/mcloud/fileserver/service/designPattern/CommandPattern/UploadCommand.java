package com.mcloud.fileserver.service.designPattern.CommandPattern;

import com.alibaba.fastjson.JSONObject;
import com.mcloud.fileserver.service.file.fileOperate.upload.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author: vellerzheng
 * @Description:
 * @Date:Created in 15:38 2018/6/6
 * @Modify By:
 */

@Component
public class UploadCommand implements Command {


    private UploadService uploadService;

    UploadCommand(){

    }

    public  UploadCommand(UploadService uploadService){
        this.uploadService = uploadService;
    }



    @Override
    public void exec() {
        uploadService.uploadFile();
    }
}
