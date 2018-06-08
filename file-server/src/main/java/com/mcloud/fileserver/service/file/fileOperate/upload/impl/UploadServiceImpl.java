package com.mcloud.fileserver.service.file.fileOperate.upload.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mcloud.fileserver.repository.entity.*;
import com.mcloud.fileserver.service.cloud.CloudService;
import com.mcloud.fileserver.service.designPattern.cloudAbstractFactory.*;
import com.mcloud.fileserver.service.file.fileOperate.upload.UploadService;
import com.mcloud.fileserver.service.provider.CloudInfoService;
import com.mcloud.fileserver.util.FileEncAndDecByDES;
import com.mcloud.fileserver.util.FileManage;
import com.mcloud.fileserver.util.PartitionFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: vellerzheng
 * @Description:
 * @Date:Created in 15:45 2018/6/6
 * @Modify By:
 */

@Component
public class UploadServiceImpl implements UploadService {

    private JSONObject jsonObject;

    @Autowired
    MulThreadEncryAndUpload mulThEncUp;

    @Autowired
    CloudInfoService cloudInfoService;

    UploadServiceImpl(){

    }

    public UploadServiceImpl(JSONObject jsonObject){
        this.jsonObject = jsonObject;
    }

    @Override
    public void uploadFile() {
        Map<String, CloudService> map = new HashMap<>(); // k文件路径， Object 云服务操作
        System.out.println("=========================================="+jsonObject.toJSONString());
        //重命名
        String filePath = jsonObject.getString("filePath");
        Integer id = jsonObject.getInteger("id");
        String usrName = jsonObject.getString("userName");
        Integer cloudNumber = jsonObject.getInteger("cloudNumber");



        List<CloudService> cloudServices = getCloudService(jsonObject);


        File file = new File(filePath);
        long fileSize = file.length();

        if(fileSize <= 1024*1024*4) {

            //小于 4M 直接加密
            FileEncAndDecByDES td = new FileEncAndDecByDES(usrName + id);
            String md5FileName = FileManage.getMD5ByFile(file) +
                                file.getName().substring(file.getName().lastIndexOf("."), file.getName().length());
            File encry = td.encrypt(file,md5FileName);

        }else{
            int partSize = (int) (fileSize /1024/ 1024 / (cloudNumber-1));
            List<String> fileNames = PartitionFile.split(file,partSize);
            map = matchFilePathToCloudService(fileNames, cloudServices);
            // 多线程加密上传
            mulThEncUp = new MulThreadEncryAndUpload(map,usrName+id);
          //  mulThEncUp.EncryAndUpload();
            JSONObject json =  mulThEncUp.EncryAndUpload();
            RestTemplate template = new RestTemplate();
            String url = "http://localhost:8765/cloudPath";
            String result = template.postForObject(url,json,String.class);
            System.out.println(result);
      //     cloudInfoService.provideCloudPath(jsonObject);
        }


        return ;
    }



    public Map<String, CloudService> matchFilePathToCloudService(List<String> filePaths, List<CloudService> cloudServices){
        if(filePaths == null || cloudServices ==null)
            return null;
        Map<String, CloudService> map = new HashMap<>();
        if(filePaths.size() <= cloudServices.size()){
            for(int i = 0; i< filePaths.size(); i++)
                map.put(filePaths.get(i), cloudServices.get(i));
        }else{

            int cycle = filePaths.size() / cloudServices.size();
            for( int i = 1; i <= cycle; i++){
                List<String> tmp = filePaths.subList((i-1) * cloudServices.size(), i * cloudServices.size());
                for( int j = 0; j < tmp.size(); j++){
                    map.put(tmp.get(j), cloudServices.get(j));
                }
            }

            List<String> suffixTmp = filePaths.subList(cycle*cloudServices.size(),filePaths.size());
            for(int n = 0; n < suffixTmp.size(); n++)
                map.put(suffixTmp.get(n),cloudServices.get(n));
        }

        return map;
    }


    /**
     * 工厂模式实例化云服务
     * @param jsonObject 解析rabbitmq 接收的json
     * @return
     */
   public List<CloudService> getCloudService(JSONObject jsonObject){
        List<CloudService> cloudSer = new ArrayList<>();
        ConfAliyun confAliyun = JSON.parseObject(jsonObject.get("aliyun").toString(), ConfAliyun.class);
        ConfNetease confNetease = JSON.parseObject(jsonObject.get("netease").toString(), ConfNetease.class);
        ConfQcloud confQcloud = JSON.parseObject(jsonObject.get("qcloud").toString(), ConfQcloud.class);
        ConfQiniu confQiniu = JSON.parseObject(jsonObject.get("qiniu").toString(),ConfQiniu.class);
        ConfUpyun confUpyun = JSON.parseObject(jsonObject.get("upyun").toString(),ConfUpyun.class);

        if(confAliyun != null) {
            Provider proAliyun = new AliyunFactory();
            CloudService cloudServiceAliyun = proAliyun.produce(confAliyun);
            cloudSer.add(cloudServiceAliyun);
            //  map.put(resFileName.get(0), cloudServiceAliyun);
        }

        if(confNetease != null){
            Provider proNetease = new NetEaseFactory();
            CloudService cloudServiceNetease = proNetease.produce(confNetease);
            cloudSer.add(cloudServiceNetease);
        }
        if(confQcloud != null){
            Provider proQcloud = new QcloudFactory();
            CloudService cloudServiceQcloud = proQcloud.produce(confQcloud);
            cloudSer.add(cloudServiceQcloud);
        }
        if(confQiniu != null){
            Provider proQiniu = new QiniuFactory();
            CloudService cloudServiceQiniu = proQiniu.produce(confQiniu);
            cloudSer.add(cloudServiceQiniu);
        }
        if(confUpyun != null){
            Provider proUpyun = new UpyunFactory();
            CloudService cloudServiceUpyun = proUpyun.produce(confUpyun);
            cloudSer.add(cloudServiceUpyun);
        }

        return cloudSer;
    }

}
