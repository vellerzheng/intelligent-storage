package com.mcloud.fileserver.service.file.fileOperate.delete.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mcloud.fileserver.repository.entity.*;
import com.mcloud.fileserver.service.cloud.CloudService;
import com.mcloud.fileserver.service.designPattern.cloudAbstractFactory.*;
import com.mcloud.fileserver.service.file.fileOperate.delete.DeleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: vellerzheng
 * @Description:
 * @Date:Created in 15:45 2018/6/6
 * @Modify By:
 */
@Component
public class DeleteServiceImpl implements DeleteService {

    private JSONObject jsonObject;

    @Autowired
    MulThreadDeleteFile mulDelFile;

    DeleteServiceImpl(){

    }

    public DeleteServiceImpl(JSONObject jsonObject){
        this.jsonObject = jsonObject;
    }

    @Override
    public void deleteFile() {

        Map<String, CloudService> map = new HashMap<>(); // k文件路径， Object 云服务操作
        //重命名
        String localFilePath = jsonObject.getString("filePath");
        Integer userId = jsonObject.getInteger("userId");
        String userName = jsonObject.getString("userName");
        Integer cloudNumber = jsonObject.getInteger("cloudNumber");
        String realFileName = jsonObject.getString("fileName");
        FileHash fileHash = JSON.parseObject(jsonObject.getJSONObject("fileHash").toJSONString(), FileHash.class);

        Map<String,CloudService> deleteMap =MatchCloudFilePathToCloudService(fileHash,jsonObject);
        mulDelFile = new MulThreadDeleteFile(deleteMap);
        boolean resDelFile = mulDelFile.deleteFileByMulCloud();

        System.out.println("delete file result: " + resDelFile);

    }


    /**
     * 将云文件路径与云操作匹配
     * @param jsonObject 解析rabbitmq 接收的json
     * @return
     */
    public Map<String,CloudService> MatchCloudFilePathToCloudService(FileHash fileHash, JSONObject jsonObject){
        Map<String, CloudService> map = new HashMap<>();
        ConfAliyun confAliyun = JSON.parseObject(jsonObject.get("aliyun").toString(), ConfAliyun.class);
        ConfNetease confNetease = JSON.parseObject(jsonObject.get("netease").toString(), ConfNetease.class);
        ConfQcloud confQcloud = JSON.parseObject(jsonObject.get("qcloud").toString(), ConfQcloud.class);
        ConfQiniu confQiniu = JSON.parseObject(jsonObject.get("qiniu").toString(),ConfQiniu.class);
        ConfUpyun confUpyun = JSON.parseObject(jsonObject.get("upyun").toString(),ConfUpyun.class);

        if(confAliyun != null && fileHash.getAliyun() != null) {
            Provider proAliyun = new AliyunFactory();
            CloudService cloudServiceAliyun = proAliyun.produce(confAliyun);
            map.put(fileHash.getAliyun(), cloudServiceAliyun);
        }

        if(confNetease != null && fileHash.getNetease() != null){
            Provider proNetease = new NetEaseFactory();
            CloudService cloudServiceNetease = proNetease.produce(confNetease);
            map.put(fileHash.getNetease(),cloudServiceNetease);
        }
        if(confQcloud != null && fileHash.getQcloud() != null){
            Provider proQcloud = new QcloudFactory();
            CloudService cloudServiceQcloud = proQcloud.produce(confQcloud);
            map.put(fileHash.getQcloud(), cloudServiceQcloud);
        }
        if(confQiniu != null && fileHash.getQiniu() != null){
            Provider proQiniu = new QiniuFactory();
            CloudService cloudServiceQiniu = proQiniu.produce(confQiniu);
            map.put(fileHash.getQiniu(),cloudServiceQiniu);
        }
        if(confUpyun != null && fileHash.getUpyun() != null){
            Provider proUpyun = new UpyunFactory();
            CloudService cloudServiceUpyun = proUpyun.produce(confUpyun);
            map.put(fileHash.getUpyun(),cloudServiceUpyun);
        }

        return map;
    }
}
