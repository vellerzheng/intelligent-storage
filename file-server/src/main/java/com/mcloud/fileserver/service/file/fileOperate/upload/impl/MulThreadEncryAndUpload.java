package com.mcloud.fileserver.service.file.fileOperate.upload.impl;


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
 * @Date:Created in 21:27 2018/6/7
 * @Modify By:
 */
@Component
public class MulThreadEncryAndUpload {


    private Map<String, CloudService> map; // k文件路径，Object云服务操作
    private String encryptKey;

    MulThreadEncryAndUpload(){
    }

    MulThreadEncryAndUpload( Map<String, CloudService> map,String encryptKey){
        this.map = map;
        this.encryptKey = encryptKey;
    }

    JSONObject EncryAndUpload(){

        JSONObject jsonObject = new JSONObject();
        List<Future<Pair<String, String>>> futures = new ArrayList<>();
        ExecutorService es = Executors.newFixedThreadPool(4);

        for(Map.Entry<String, CloudService> entry : map.entrySet()){
            futures.add(es.submit(new Task(entry.getKey(), entry.getValue(),encryptKey)));
        }

        for(Future<Pair<String, String>> res : futures){
            try {
                jsonObject.put(res.get().getKey(), res.get().getValue());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        es.shutdown();
        //System.out.println("**********JSONobject***** " + jsonObject.toString());

        return jsonObject;

    }

    private class Task implements Callable<Pair<String,String>> {

        private  String filePath;
        private  CloudService cloudService;
        private String encryptKey;

        Task(String filePath,CloudService cloudService,String encryptKey){
            this.filePath = filePath;
            this.cloudService = cloudService;
            this.encryptKey = encryptKey;

        }
        /**
         * Computes a result, or throws an exception if unable to do so.
         *
         * @return computed result
         * @throws Exception if unable to compute a result
         */

        @Override
        public Pair<String, String> call() throws Exception {
            FileEncAndDecByDES encDes = new FileEncAndDecByDES(encryptKey);
            String encryptPath = encDes.encrypt(new File(filePath));
            Pair<String, String> pair = cloudService.uploadFile(encryptPath);
            FileManage.deleteFile(filePath);
            FileManage.deleteFile(encryptPath);
            return  pair;
        }
    }



}
