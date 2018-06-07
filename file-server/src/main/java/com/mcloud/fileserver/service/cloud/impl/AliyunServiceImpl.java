package com.mcloud.fileserver.service.cloud.impl;

/**
 * Created by vellerzheng on 2017/9/19.
 */

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.mcloud.fileserver.repository.entity.ConfAliyun;
import com.mcloud.fileserver.service.cloud.AliyunService;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 * 对OSS服务器进行上传删除等的处理
 *
 * @ClassName: AliyunServiceImpl
 * @Description:
 * @date 2017-5-3 上午10:47:00
 *
 */
@Service
public class AliyunServiceImpl implements AliyunService {



/*    @Autowired
    ConfAliyunRespository confAliyunRespository;*/

     private ConfAliyun confAliyun;
     private OSSClient ossClient;

     public AliyunServiceImpl(){

     }


    public void initAliyun(ConfAliyun confAliyun){
         this.confAliyun = confAliyun;
         if(ossClient == null) {
             ossClient = new OSSClient(confAliyun.getEndpoint(), confAliyun.getAccesskey(),
                     confAliyun.getAccesskeysecret());
         }
    }
    /**
     * 上传本地文件      @Title: uploadFile
     */
    public boolean uploadFile(ConfAliyun confAliyun, String localFilePath)  {
  /*      confAliyun confAliyun = null;
        confAliyun = new confAliyun(endpoint,accessKey,accessKeySecret,bucketName,accessUrl);
        OSSClient ossClient = new OSSClient(confAliyun.getEndpoint(), confAliyun.getAccessKeyId(),
                confAliyun.getAccessKeySecret());*/
        initAliyun(confAliyun);
        String fileName = localFilePath.substring((localFilePath.lastIndexOf(File.separator)));
        String yunfileName ="backupFile/"+ fileName.replace(File.separator,"");  //key 为上传的文件名
        ossClient.putObject(confAliyun.getBucketname(),yunfileName,new File(localFilePath));
        /*ossClient.shutdown();*/
        return true;
    }



    /**
     * 上传OSS服务器文件 @Title: uploadMultipartFile
     *  @param multipartFile spring 上传的文件
     * remotePath @param oss服务器二级目录
     *  @throws Exception 设定文件 @return String
     * 返回类型 返回oss存放路径 @throws
     */
    public  String uploadMultipartFile(ConfAliyun confAliyun, MultipartFile multipartFile, String remotePath) throws IOException {
        // 流转换 将MultipartFile转换为oss所需的InputStream
        CommonsMultipartFile cf = (CommonsMultipartFile) multipartFile;
        DiskFileItem fi = (DiskFileItem) cf.getFileItem();
        InputStream fileContent = fi.getInputStream();
        //获取文件名，对文件名做随机处理
        String fileName = fi.getName();
        fileName = "lxkc_" + new Date().getTime() + fileName.substring(fileName.lastIndexOf("."));
        // 加载配置文件，初始化OSSClient
 /*       confAliyun confAliyun = new confAliyun("conf/accessCloud.properties");
        OSSClient ossClient = new OSSClient(confAliyun.getEndpoint(), confAliyun.getAccessKeyId(),
                confAliyun.getAccessKeySecret());*/
        // 定义二级目录
        String remoteFilePath = remotePath.substring(0, remotePath.length()).replaceAll("\\\\", "/") + "/";
        // 创建上传Object的Metadata
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(fileContent.available());
        objectMetadata.setContentEncoding("utf-8");
        objectMetadata.setCacheControl("no-cache");
        objectMetadata.setHeader("Pragma", "no-cache");
        objectMetadata.setContentType(contentType(fileName.substring(fileName.lastIndexOf("."))));
        objectMetadata.setContentDisposition("inline;filename=" + fileName);
        // 上传文件
        ossClient.putObject(confAliyun.getBucketname(), remoteFilePath + fileName, fileContent, objectMetadata);
        // 关闭OSSClient
        ossClient.shutdown();
        // 关闭io流
        fileContent.close();
        return confAliyun.getAccessurl() + "/" + remoteFilePath + fileName;
    }

    // 下载文件
    @SuppressWarnings("unused")
    public  void downloadFile(ConfAliyun confAliyun, String yunfileName, String saveLocalFilePath) {
                initAliyun(confAliyun);
                String yunFilePath="backupFile/"+yunfileName;
                /*confAliyun confAliyun = null;*/
                try {
   /*                 confAliyun = new confAliyun("conf/accessCloud.properties");
                    // 初始化OSSClient
                    OSSClient ossClient = new OSSClient(confAliyun.getEndpoint(), confAliyun.getAccessKeyId(),
                            confAliyun.getAccessKeySecret());*/
                    OSSObject object = ossClient.getObject(confAliyun.getBucketname(), yunFilePath);
                    // 获取ObjectMeta
                    ObjectMetadata meta = object.getObjectMetadata();

                    // 获取Object的输入流
                    InputStream objectContent = object.getObjectContent();
                    String fileName =yunFilePath.substring((yunFilePath.lastIndexOf("/")));
                    String localFilePath = saveLocalFilePath+File.separator+ fileName.replace("/","");  //key 为上传的文件名
                    ObjectMetadata objectData = ossClient.getObject(new GetObjectRequest(confAliyun.getBucketname(), yunFilePath),
                            new File(localFilePath));
                    // 关闭数据流
                    objectContent.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

    /**
     * 根据key删除OSS服务器上的文件 @Title: deleteFile @Description: @param @param
     * confAliyun @param 配置文件实体 @param filePath 设定文件 @return void 返回类型 @throws
     */
    public  void deleteFile(ConfAliyun confAliyun, String fileName) {
        initAliyun(confAliyun);
        String yunfilePath="backupFile/"+fileName;
/*        confAliyun confAliyun = null;
        try {
            confAliyun = new confAliyun("conf/accessCloud.properties");
        } catch (IOException e) {
            e.printStackTrace();
        }
        OSSClient ossClient = new OSSClient(confAliyun.getEndpoint(), confAliyun.getAccessKeyId(),
                            confAliyun.getAccessKeySecret()); */
        ossClient.deleteObject(confAliyun.getBucketname(), yunfilePath);
    }

    /**
     * Description: 判断OSS服务文件上传时文件的contentType @Version1.0
     *
     * @param FilenameExtension
     *            文件后缀
     * @return String
     */
    public static String contentType(String FilenameExtension) {
        if (FilenameExtension.equals(".BMP") || FilenameExtension.equals(".bmp")) {
            return "image/bmp";
        }
        if (FilenameExtension.equals(".GIF") || FilenameExtension.equals(".gif")) {
            return "image/gif";
        }
        if (FilenameExtension.equals(".JPEG") || FilenameExtension.equals(".jpeg") || FilenameExtension.equals(".JPG")
                || FilenameExtension.equals(".jpg") || FilenameExtension.equals(".PNG")
                || FilenameExtension.equals(".png")) {
            return "image/jpeg";
        }
        if (FilenameExtension.equals(".HTML") || FilenameExtension.equals(".html")) {
            return "text/html";
        }
        if (FilenameExtension.equals(".TXT") || FilenameExtension.equals(".txt")) {
            return "text/plain";
        }
        if (FilenameExtension.equals(".VSD") || FilenameExtension.equals(".vsd")) {
            return "application/vnd.visio";
        }
        if (FilenameExtension.equals(".PPTX") || FilenameExtension.equals(".pptx") || FilenameExtension.equals(".PPT")
                || FilenameExtension.equals(".ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (FilenameExtension.equals(".DOCX") || FilenameExtension.equals(".docx") || FilenameExtension.equals(".DOC")
                || FilenameExtension.equals(".doc")) {
            return "application/msword";
        }
        if (FilenameExtension.equals(".XML") || FilenameExtension.equals(".xml")) {
            return "text/xml";
        }
        if (FilenameExtension.equals(".apk") || FilenameExtension.equals(".APK")) {
            return "application/octet-stream";
        }
        return "text/html";
    }

    public static void main(String[] args) {

/*        AliyunOSS aliyun= new AliyunOSS();
        String locaFilePath="D:\\Test\\split\\README.txt";
        aliyun.uploadFile(locaFilePath);

        String yunFileName="README.txt";
        String saveLocalFilePath="D:\\Test\\merge";
        aliyun.downloadFile(yunFileName,saveLocalFilePath);
        aliyun.deleteFile(yunFileName);*/
    }
}
