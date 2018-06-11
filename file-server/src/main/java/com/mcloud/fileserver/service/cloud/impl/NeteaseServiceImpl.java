package com.mcloud.fileserver.service.cloud.impl;



import com.mcloud.fileserver.repository.entity.ConfNetease;
import com.mcloud.fileserver.service.cloud.CloudService;
import com.netease.cloud.ClientConfiguration;
import com.netease.cloud.Protocol;
import com.netease.cloud.auth.BasicCredentials;
import com.netease.cloud.auth.Credentials;
import com.netease.cloud.services.nos.NosClient;
import com.netease.cloud.services.nos.model.*;
import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vellerzheng on 2017/9/21.
 */
@Service
public class NeteaseServiceImpl implements CloudService {


    private ConfNetease confNetease;

    private NosClient nosClient;

    final  static Logger logger = LoggerFactory.getLogger(NeteaseServiceImpl.class);

    NeteaseServiceImpl(){

    }

    public NeteaseServiceImpl(ConfNetease confNetease){
        this.confNetease = confNetease;
    }
    /**
     *
     * @Title: getNosClient
     * @Description: 生成客户端对象
     * @return
     */
    public  void initNosClient() {

        // 初始化秘钥信息

  /*      String secretId = "657c5001cf0c46ee92df876415d04033";
        String secretKey = "e660f8c5185d4317a3a3cff8447fcc4f";
        String endPoint = "nos-eastchina1.126.net";*/

       if(nosClient == null) {
           // 初始化秘钥信息
           Credentials cred = new BasicCredentials(confNetease.getSecretid(), confNetease.getSecretkey());
           // 初始化客户端配置
           ClientConfiguration conf = new ClientConfiguration();
           // 设置 NosClient 使用的最大连接数
           conf.setMaxConnections(200);
           // 设置 socket 超时时间
           conf.setSocketTimeout(10000);
           // 设置失败请求重试次数
           conf.setMaxErrorRetry(2);
           // 如果要用 https 协议，请加上下面语句
           conf.setProtocol(Protocol.HTTPS);
           // 生成客户端
           // 初始化cosClient
           nosClient = new NosClient(cred, conf);
           nosClient.setEndpoint(confNetease.getEndpoint());
       }

    }

    /**
     *
     * @Title: uploadFile
     * @Description:上传文件
     */
    public   String uploadMultiPartFile(String localFilePath) {
        initNosClient();
        String fileName = localFilePath.substring((localFilePath.lastIndexOf(File.separator)));
        String yunfileName = fileName.replace(File.separator,"");  //key 为上传的文件名

      //  UploadFileRequest uploadFileRequest = new UploadFileRequest(bucketName,yunfilePath, localFilePath);
      //  String uploadFileRet = getCOSClient().uploadFile(uploadFileRequest);
        //初始化一个分块上传，获取分块上传ID，桶名 + 对像名 + 分块上传ID 唯一确定一个分块上传
        String contentType=null;
        InputStream is=null;
        try {
            is = new FileInputStream(localFilePath);
            Path path= Paths.get(localFilePath);
            contentType= Files.probeContentType(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        InitiateMultipartUploadRequest initRequest = new InitiateMultipartUploadRequest(confNetease.getBucketname(), localFilePath);
      //你还可以在初始化分块上传时，设置文件的Content-Type
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(contentType);
        initRequest.setObjectMetadata(objectMetadata);
        InitiateMultipartUploadResult initResult = nosClient.initiateMultipartUpload(initRequest);
        String uploadId = initResult.getUploadId();


        //分块上传的最小单位为16K，最后一块可以小于16K，每个分块都得标识一个唯一的分块partIndex
        int partIndex=0;
              /* 设定每次写入1024bytes */
              /*
        int bufferSize = 1073741824;
        byte[] buffer = new byte[bufferSize];
        long readLen;
        try {
            while ((readLen = is.read(buffer, 0, bufferSize)) != -1 ){
                InputStream partStream = new ByteArrayInputStream(buffer);
               getNosClient().uploadPart(new UploadPartRequest().withBucketName(bucketName)
                        .withUploadId(uploadId).withInputStream(partStream)
                        .withKey(yunfileName).withPartSize(readLen).withPartNumber(partIndex));
                partIndex++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
      */

        //这里可以检查分块是否全部上传，分块MD5是否与本地计算相符，如果不符或者缺少可以重新上传
        List partETags = new ArrayList();

        int nextMarker = 0;
        while (true) {
            ListPartsRequest listPartsRequest = new ListPartsRequest(confNetease.getBucketname(), yunfileName, uploadId);
            listPartsRequest.setPartNumberMarker(nextMarker);

            PartListing partList = nosClient.listParts(listPartsRequest);

            for (PartSummary ps : partList.getParts()) {
                nextMarker++;
                partETags.add(new PartETag(ps.getPartNumber(), ps.getETag()));
            }

            if (!partList.isTruncated()) {
                break;
            }
        }
        CompleteMultipartUploadRequest completeRequest =  new CompleteMultipartUploadRequest(confNetease.getBucketname(),yunfileName, uploadId, partETags);
        CompleteMultipartUploadResult completeResult = nosClient.completeMultipartUpload(completeRequest);

        return yunfileName;
    }

    /**
     *
     * @Title: uploadFile
     * @Description: 简单不分片上传文件
     * @return
     */
    public Pair<String, String> uploadFile(String localFilePath) {

        initNosClient();
        String fileName = localFilePath.substring((localFilePath.lastIndexOf(File.separator)));
        String yunfileName = fileName.replace(File.separator,"");  //key 为上传的文件名
        try {
            nosClient.putObject(confNetease.getBucketname(),yunfileName, new File(localFilePath));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return new Pair<>("netease",yunfileName);
    }

    /**
     *
     * @Title: downFile
     * @Description: 下载文件
     * @return
     */
    public  String downLoadFile(String cloudFilePath,String localFilePath) {

        initNosClient();
        String fileName =cloudFilePath.substring((cloudFilePath.lastIndexOf("/")));
        String savelocalFilePath = localFilePath + File.separator+ fileName.replace("/","");  //key 为上传的文件名
        GetObjectRequest getObjectRequest = new GetObjectRequest(confNetease.getBucketname(),cloudFilePath);
        ObjectMetadata objectMetadata = nosClient.getObject(getObjectRequest,new File(savelocalFilePath));
        return savelocalFilePath;
    }


    /**
     *
     * @Title: deleteFile
     * @Description: 删除文件
     * @return
     */
    public boolean deleteFile(String cloudFilePath) {

        initNosClient();
        boolean isExist = nosClient.doesObjectExist(confNetease.getBucketname(),cloudFilePath);
        if(isExist)
            nosClient.deleteObject(confNetease.getBucketname(),cloudFilePath);
        else
            return  false;
        return true;
    }



/*    public static void main(String[] args){
        NeteaseServiceImpl netease = new NeteaseServiceImpl();
        String localFilePath="D:\\Test\\split\\README.txt";
        netease.uploadFile(localFilePath);
        System.out.println("Upload file successful!");

        String yunFileName = "README.txt";
        String saveLocalFilePath ="D:\\Test\\merge";
        netease.downFile(yunFileName,saveLocalFilePath);
        netease.deleteFile(yunFileName);

    }*/


}
