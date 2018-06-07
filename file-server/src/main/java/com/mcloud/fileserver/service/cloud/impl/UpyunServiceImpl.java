package com.mcloud.fileserver.service.cloud.impl;


import com.mcloud.fileserver.repository.entity.ConfUpyun;
import com.mcloud.fileserver.service.cloud.UpyunService;
import main.java.com.UpYun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Created by vellerzheng on 2017/8/25.
 */
@Service
public class UpyunServiceImpl implements UpyunService {
/*    private String bucketName ="youpaiyunfile";
    private String userName = "guyun";
    private String password ="qwe123456";*/


    private ConfUpyun confUpyun;

    private UpYun upYun;


    private void initUpyunClient(ConfUpyun confUpyun){
        // 可选属性1，是否开启 debug 模式，默认不开启
        if(confUpyun == null)
            this.confUpyun =confUpyun;
        if(upYun == null ) {
            upYun = new UpYun(confUpyun.getBucketname(), confUpyun.getUsername(), confUpyun.getPassword());
            upYun.setDebug(false);
            // 可选属性2，超时时间，默认 30s
            upYun.setTimeout(30);
        }

    }

    public  void createYunFilePath(ConfUpyun confUpyun){
        initUpyunClient(confUpyun);
        // 创建目录
        boolean result = upYun.mkDir("/up/tt");
    }

    // 文件上传
    public boolean uploadFile(ConfUpyun confUpyun, String localFilePath){
        initUpyunClient(confUpyun);
        String fileName = localFilePath.substring((localFilePath.lastIndexOf(File.separator)));
        //key 为上传的文件名
        String yunfilePath = "/up/tt/"+ fileName.replace(File.separator,"");
        File file = new File(localFilePath);
        boolean result = false;
        try {
            // 计算文件 MD5，如果文件太大或计算不便，可以不计算
            upYun.setContentMD5(UpYun.md5(file));
            result = upYun.writeFile(yunfilePath, file,true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean deleteYunFile(ConfUpyun confUpyun, String fileName){
        initUpyunClient(confUpyun);
        String yunFileName = "/up/tt/"+fileName;
        // 删除目录
        boolean result = upYun.rmDir(yunFileName);
        return result;
    }

    public boolean downloadFile(ConfUpyun confUpyun, String fileName,String saveFilePath){
        initUpyunClient(confUpyun);
        String yunFileName = "/up/tt/"+fileName;
        File file= new File(saveFilePath);
        boolean result=upYun.readFile(yunFileName,file);
        return result;
    }

    public void getFileInformation(ConfUpyun confUpyun, String fileName){
        initUpyunClient(confUpyun);
        String yunFilePath = "/up/tt/"+fileName;
        Map<String,String> result = upYun.getFileInfo(yunFilePath);
        for (Map.Entry<String, String> entry : result.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }
    }

    public void getSpaceCapacity(ConfUpyun confUpyun){
        initUpyunClient(confUpyun);
        long usage = upYun.getBucketUsage();
        System.out.println("BucketUsage:"+usage);
    }


 /*   public static  void main(String[] args){
        String localFilePath="D:\\Test\\split\\云Hadoop，The Definitive Guide.pdf-4.dat";
        Upyun upyun=new Upyun();
      //  upyun.Upyun();
    //   upyun.createYunFilePath();
         upyun.uploadFile(localFilePath);

        String yunfile="云Hadoop，The Definitive Guide.pdf-4.dat";
        upyun.getFileInformation(yunfile);
        upyun.getSpaceCapacity();

        String saveFilePath="D:\\Test\\merge\\云Hadoop，The Definitive Guide.pdf-4.dat";
        upyun.downloadFile(yunfile,saveFilePath);
        String fileName="云Hadoop，The Definitive Guide.pdf-4.dat";
     //   upyun.deleteYunFile(fileName);

    }*/
}
