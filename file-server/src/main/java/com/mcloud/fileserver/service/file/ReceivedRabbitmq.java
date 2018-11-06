package com.mcloud.fileserver.service.file;

import com.alibaba.fastjson.JSONObject;
import com.mcloud.fileserver.service.designPattern.MqCommandPattern.*;
import com.mcloud.fileserver.service.file.fileOperate.delete.DeleteService;
import com.mcloud.fileserver.service.file.fileOperate.delete.impl.DeleteServiceImpl;
import com.mcloud.fileserver.service.file.fileOperate.download.DownLoadService;
import com.mcloud.fileserver.service.file.fileOperate.download.impl.DownLoadServiceImpl;
import com.mcloud.fileserver.service.file.fileOperate.upload.UploadService;
import com.mcloud.fileserver.service.file.fileOperate.upload.impl.UploadServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


/**
 * @Author: vellerzheng
 * @Description:
 * @Date:Created in 14:14 2018/6/6
 * @Modify By:
 */
@Component
public class ReceivedRabbitmq  {
    private JSONObject jsonObject;

    private final static Logger logger = LoggerFactory.getLogger(ReceivedRabbitmqRunnable.class);

    public ReceivedRabbitmq(){}

    public  ReceivedRabbitmq(JSONObject jsonObject){
        this.jsonObject = jsonObject;
    }

    @Autowired
    UploadService uploadService;
    @Autowired
    DownLoadService downLoadService;
    @Autowired
    DeleteService deleteService;
    @Autowired
    Invoker invoker;
    @Autowired
    @Qualifier("uploadCommand")
    Command cmdUp;
    @Autowired
    @Qualifier("downLoadCommand")
    Command cmdDown;
    @Autowired
    @Qualifier("deleteCommand")
    Command cmdDel;


    @Async
    public void execCommand() {

        if(jsonObject.get("command").toString().equals("upload")){
            uploadService = new UploadServiceImpl(jsonObject);
            cmdUp = new UploadCommand(uploadService);
            invoker = new Invoker(cmdUp);
            invoker.action();
        }

        if(jsonObject.get("command").toString().equals("download")){
            downLoadService = new DownLoadServiceImpl(jsonObject);
            cmdDown = new DownLoadCommand(downLoadService);
            invoker = new Invoker(cmdDown);
            invoker.action();
        }

        if(jsonObject.get("command").toString().equals("delete")){
            deleteService = new DeleteServiceImpl(jsonObject);
            cmdDel = new DeleteCommand(deleteService);
            invoker = new Invoker(cmdDel);
            invoker.action();
        }

    }
}

