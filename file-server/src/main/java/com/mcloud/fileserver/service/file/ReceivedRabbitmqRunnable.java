package com.mcloud.fileserver.service.file;

import com.alibaba.fastjson.JSONObject;
import com.mcloud.fileserver.service.designPattern.CommandPattern.*;
import com.mcloud.fileserver.service.file.fileOperate.delete.DeleteService;
import com.mcloud.fileserver.service.file.fileOperate.delete.DeleteServiceImpl;
import com.mcloud.fileserver.service.file.fileOperate.download.DownLoadService;
import com.mcloud.fileserver.service.file.fileOperate.download.DownLoadServiceImpl;
import com.mcloud.fileserver.service.file.fileOperate.upload.UploadService;
import com.mcloud.fileserver.service.file.fileOperate.upload.UploadServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


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
        try {

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

            System.out.println("操作命令：" + jsonObject.get("command"));
            System.out.println("消费的用户id：" + jsonObject.get("id"));
            System.out.println("消费的用户名" + jsonObject.get("userName"));
            System.out.println("消费的文件路径为：" + jsonObject.get("filePath"));
            System.out.println("消费的云数量为： " + jsonObject.get("cloudNumber"));
            System.out.println("-----------consumer aliyun ------" + jsonObject.get("aliyun").toString());
            System.out.println("-----------consumer netease ------" + jsonObject.get("netease").toString());
            System.out.println("-----------consumer qcloud ------" + jsonObject.get("qcloud").toString());
            System.out.println("-----------consumer qiniu ------" + jsonObject.get("qiniu").toString());
            System.out.println("-----------consumer upyun ------" + jsonObject.get("upyun").toString());

            Thread.sleep(2000);
        }catch (InterruptedException e){
            e.printStackTrace();
            logger.error("接收Rabbit信息处理错误" + e.toString());

        }
    }
}
