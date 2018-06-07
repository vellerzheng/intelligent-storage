package com.mcloud.storageweb.service.file.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mcloud.storageweb.repository.entity.*;
import com.mcloud.storageweb.service.cloudConf.*;
import com.mcloud.storageweb.service.file.FileService;
import com.mcloud.storageweb.service.rabbitmq.RabitMqProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author: vellerzheng
 * @Description:
 * @Date:Created in 19:45 2018/6/5
 * @Modify By:
 */
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    ConfAliyunService confAliyunService;
    @Autowired
    ConfNeteaseService confNeteaseService;
    @Autowired
    ConfQcloudService confQcloudService;
    @Autowired
    ConfQiniuService confQiniuService;
    @Autowired
    ConfUpyunService confUpyunService;

    @Autowired
    RabitMqProvider rabitMqProvider;

    @Override
    public boolean tranferEventToFileServer(User user, String filePath) {
        if(user == null || filePath == null)
            return false;

        int count = 0;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("command","upload");
        jsonObject.put("id", user.getId());
        jsonObject.put("userName", user.getUsername());
        jsonObject.put("filePath", filePath);

        ConfAliyun  confAliyun = confAliyunService.selectByUserIdAndStatus(user.getId());
        ConfNetease confNetease = confNeteaseService.selectByUserIdAndStatus(user.getId());
        ConfQcloud confQcloud = confQcloudService.selectByUserIdAndStatus(user.getId());
        ConfQiniu confQiniu = confQiniuService.selectByUserIdAndStatus(user.getId());
        ConfUpyun confUpyun = confUpyunService.selectByUserIdAndStatus(user.getId());

        if(confAliyun != null) {
            jsonObject.put("aliyun",  JSON.toJSONString(confAliyun));
            ++count;
        }
        if(confNetease != null){
            jsonObject.put("netease", JSON.toJSONString(confNetease));
            ++count;
        }
        if(confQcloud != null){
            jsonObject.put("qcloud", JSON.toJSONString(confQcloud));
            ++count;
        }
        if(confQiniu != null){
            jsonObject.put("qiniu", JSON.toJSONString(confQiniu));
            ++count;
        }
        if(confUpyun != null){
            jsonObject.put("upyun", JSON.toJSONString(confUpyun));
            ++count;
        }

        if(count == 0){
           confAliyun = confAliyunService.selectByUserIdAndStatus(1);
           confNetease = confNeteaseService.selectByUserIdAndStatus(1);
           confQcloud = confQcloudService.selectByUserIdAndStatus(1);
           confQiniu = confQiniuService.selectByUserIdAndStatus(1);
           confUpyun = confUpyunService.selectByUserIdAndStatus(1);

           jsonObject.put("aliyun",  JSON.toJSONString(confAliyun));
           jsonObject.put("netease", JSON.toJSONString(confNetease));
           jsonObject.put("qcloud", JSON.toJSONString(confQcloud));
           jsonObject.put("qiniu", JSON.toJSONString(confQiniu));
           jsonObject.put("upyun", JSON.toJSONString(confUpyun));
           count = 5;
        }

        jsonObject.put("cloudNumber",count);
        Object obj = rabitMqProvider.sendAndReceive(jsonObject);
        System.out.println("--------Receive back ------：" + obj);
        return true;
    }
}
