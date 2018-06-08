package com.mcloud.fileserver.service.file;

import com.alibaba.fastjson.JSONObject;
import com.mcloud.fileserver.service.designPattern.MqCommandPattern.*;
import com.mcloud.fileserver.service.file.fileOperate.delete.DeleteService;
import com.mcloud.fileserver.service.file.fileOperate.delete.DeleteServiceImpl;
import com.mcloud.fileserver.service.file.fileOperate.download.DownLoadService;
import com.mcloud.fileserver.service.file.fileOperate.download.DownLoadServiceImpl;
import com.mcloud.fileserver.service.file.fileOperate.upload.UploadService;
import com.mcloud.fileserver.service.file.fileOperate.upload.impl.UploadServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @Author: vellerzheng
 * @Description:
 * @Date:Created in 14:14 2018/6/6
 * @Modify By:
 */

public class ReceivedRabbitmqRunnable implements Runnable {
    private JSONObject jsonObject;

    private final static Logger logger = LoggerFactory.getLogger(ReceivedRabbitmqRunnable.class);
    public  ReceivedRabbitmqRunnable(JSONObject jsonObject){
        this.jsonObject = jsonObject;
    }

    @Autowired
    UploadService uploadService;
    @Autowired
    DownLoadService downLoadService;
    @Autowired
    DeleteService deleteService;

    @Override
    public void run() {

        if(jsonObject.get("command").toString().equals("upload")){
            uploadService = new UploadServiceImpl(jsonObject);
            Command cmd = new UploadCommand(uploadService);
            Invoker invoker = new Invoker(cmd);
            invoker.action();
        }

        if(jsonObject.get("command").toString().equals("download")){
            downLoadService = new DownLoadServiceImpl(jsonObject);
            Command cmd = new DownLoadCommand(downLoadService);
            Invoker invoker = new Invoker(cmd);
            invoker.action();
        }

        if(jsonObject.get("command").toString().equals("delete")){
            deleteService = new DeleteServiceImpl(jsonObject);
            Command cmd = new DeleteCommand(deleteService);
            Invoker invoker = new Invoker(cmd);
            invoker.action();
        }

    }
}
