package com.mcloud.fileserver.service.file.fileOperate.delete.impl;

import com.mcloud.fileserver.service.cloud.CloudService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @Author: vellerzheng
 * @Description:
 * @Date:Created in 19:35 2018/6/11
 * @Modify By:
 */
@Component
public class MulThreadDeleteFile {

    private Map<String, CloudService> map; // k云文件路径，Object云服务操作

    MulThreadDeleteFile(){}

    public MulThreadDeleteFile(Map<String, CloudService> map){
        this.map = map;
    }

    boolean deleteFileByMulCloud() {

        List<Boolean> list = new ArrayList<>();
        List<Future<Boolean>> futures = new ArrayList<>();
        ExecutorService es = Executors.newFixedThreadPool(4);

        for (Map.Entry<String, CloudService> entry : map.entrySet()) {
            futures.add(es.submit(new Task(entry.getKey(), entry.getValue())));
        }

        for (Future<Boolean> res : futures) {

            try {
                if(!res.get())
                    return  false;
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

        }
        es.shutdown();
        return true;
    }

    private class Task implements Callable<Boolean> {

        private  String cloudFilePath;
        private  CloudService cloudService;


        Task(String cloudFilePath, CloudService cloudService){
            this.cloudFilePath = cloudFilePath;
            this.cloudService = cloudService;

        }
        /**
         * Computes a result, or throws an exception if unable to do so.
         *
         * @return computed result
         * @throws Exception if unable to compute a result
         */
        @Override
        public Boolean call() throws Exception {

            Boolean resDeleteFile = cloudService.deleteFile(cloudFilePath);
            return resDeleteFile;
        }
    }


}
