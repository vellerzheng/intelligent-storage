package com.mcloud.fileserver.service.file.fileOperate.download.impl;

import com.alibaba.fastjson.JSONObject;
import com.mcloud.fileserver.service.cloud.CloudService;
import com.mcloud.fileserver.util.FileEncAndDecByDES;
import com.mcloud.fileserver.util.FileManage;
import javafx.util.Pair;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @Author: vellerzheng
 * @Description:
 * @Date:Created in 13:42 2018/6/11
 * @Modify By:
 */
@Component
public class MulThreadDownloadAndDecry {

    private Map<String, CloudService> map; // k云文件路径，Object云服务操作
    private String localFilePath;   // 本地文件夹路径
    private String decryptKey;

    MulThreadDownloadAndDecry(){}

    public MulThreadDownloadAndDecry(Map<String, CloudService> map, String decryptKey,String localFilePath){
        this.map = map;
        this.decryptKey = decryptKey;
        this.localFilePath = localFilePath;
    }

    List<String> downloadAnddecrypt(){

        List<String> list = new ArrayList<>();
        List<Future<String>> futures = new ArrayList<>();
        ExecutorService es = Executors.newFixedThreadPool(4);

        for(Map.Entry<String, CloudService> entry : map.entrySet()){
            futures.add(es.submit(new Task(entry.getKey(), entry.getValue(), decryptKey, localFilePath)));
        }

        for(Future<String> res : futures){
            try {
                list.add(res.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        es.shutdown();
        //System.out.println("**********JSONobject***** " + jsonObject.toString());
        list.sort(String::compareTo);
        return list;

    }

    private class Task implements Callable<String> {

        private  String cloudFilePath;
        private  CloudService cloudService;
        private  String decryptKey;
        private  String localFilePath;

        Task(String cloudFilePath, CloudService cloudService,String decryptKey,String localFilePath){
            this.cloudFilePath = cloudFilePath;
            this.cloudService = cloudService;
            this.decryptKey = decryptKey;
            this.localFilePath = localFilePath;

        }
        /**
         * Computes a result, or throws an exception if unable to do so.
         *
         * @return computed result
         * @throws Exception if unable to compute a result
         */
        @Override
        public String call() throws Exception {
            FileEncAndDecByDES encDes = new FileEncAndDecByDES(decryptKey);
         //   String encryptPath = encDes.encrypt(new File(filePath));
            File downloadFile = new File(localFilePath +"/"+"download");
            if(!downloadFile.exists())
                downloadFile.mkdirs();
            String resDownLoadFile = cloudService.downLoadFile(cloudFilePath,localFilePath+"/"+"download");
            String decryptPath = encDes.decrypt(new File(resDownLoadFile));
            FileManage.deleteFile(resDownLoadFile);
            return decryptPath;
        }
    }



}
