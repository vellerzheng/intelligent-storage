package com.mcloud.fileserver.service.cloud.impl;

import com.google.gson.Gson;
import com.mcloud.fileserver.repository.entity.ConfQiniu;
import com.mcloud.fileserver.service.cloud.CloudService;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.storage.persistent.FileRecorder;
import com.qiniu.util.Auth;
import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Paths;


/**
 * Created by vellerzheng on 2017/8/24.
 */
@Service
public class QiniuServiceImpl implements CloudService {

/*    private String accessKey = "yrQJe6hknpAgTL7Spe1x138FW0AaDMn6Vh8NaXVL";
    private String secretKey = "kHO2DI6nwbSSwoSL0SYLXVbwOfo50coEB6q-WMk0";
    private String bucket = "qiniufile";*/

    ConfQiniu confQiniu;

    private Configuration cfg;
    private Auth auth;

    final  static Logger logger = LoggerFactory.getLogger(QiniuServiceImpl.class);

    public QiniuServiceImpl(){

    }

    public QiniuServiceImpl(ConfQiniu confQiniu){
        this.confQiniu = confQiniu;
    }

    private void  initQiniuClient(){


        if(cfg == null)
            cfg = new Configuration(Zone.zone2());
        //构造一个带指定Zone对象的配置类
        if(auth == null)
            auth = Auth.create(confQiniu.getAccesskey(), confQiniu.getSecretkey());
    }

    public Pair<String,String> uploadFile(String localFilePath){
            initQiniuClient();
            UploadManager uploadManager = new UploadManager(cfg);

            String fileName = localFilePath.substring((localFilePath.lastIndexOf(File.separator)));
            String key =  fileName.replace(File.separator,"");  //key 为上传的文件名

            String upToken = auth.uploadToken(confQiniu.getBucket());
            try {
                Response response = uploadManager.put(localFilePath, key, upToken);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                System.out.println(putRet.key);
                System.out.println(putRet.hash);
            } catch (QiniuException ex) {
/*                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    System.out.println(ex2.toString());
                }*/
                logger.error(ex.toString());
            }
            return new Pair<>("qiniu", key);
        }

        /*断点续传文件方式*/
    public void randomAcessUpLoadFile(String localFilePath){

        initQiniuClient();
//默认不指定key的情况下，以文件内容的hash值作为文件名
        String fileName = localFilePath.substring((localFilePath.lastIndexOf(File.separator)));
        String key =  fileName.replace(File.separator,"");  //key 为上传的文件名
        String upToken = auth.uploadToken(confQiniu.getBucket());
        String localTempDir = Paths.get(System.getenv("java.io.tmpdir"), confQiniu.getBucket()).toString();
        try {
            //设置断点续传文件进度保存目录
            FileRecorder fileRecorder = new FileRecorder(localTempDir);
            UploadManager uploadManager = new UploadManager(cfg, fileRecorder);
            try {
                Response response = uploadManager.put(localFilePath, key, upToken);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                System.out.println(putRet.key);
                System.out.println(putRet.hash);
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    logger.error(ex2.toString());
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            logger.error(ex.toString());
        }

    }
    /**
     * 从输入流中获取字节数组
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static  byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }


    public String urlDownLoadSource(String finalUrl,String cloudFilePath,String localFilePath) throws IOException {
        URL url = null;
        try {
            url = new URL(finalUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();

        //设置超时间为3秒
        conn.setConnectTimeout(3*1000);
        //防止屏蔽程序抓取而返回403错误
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

        //得到输入流
        InputStream inputStream = conn.getInputStream();

        //获取自己数组
        byte[] getData = new byte[0];
        try {
            getData = readInputStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //文件保存位置
        File saveDir = new File(localFilePath);
        if(!saveDir.exists()){
            saveDir.mkdir();
        }
        String fileName =cloudFilePath.substring((cloudFilePath.lastIndexOf("/")));
        String savelocalFilePath = localFilePath + File.separator+ fileName.replace("/","");
        File file = new File(savelocalFilePath);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(getData);
        if(fos!=null){
            fos.close();
        }
        if(inputStream!=null){
            inputStream.close();
        }

        return savelocalFilePath;
        //System.out.println("info:"+url+" download success");
    }

    @Override
    public String downLoadFile(String cloudFilePath, String localFilePath) {
        initQiniuClient();
        String encodedFileName = null;
        String savelocalFilePath = null;
        try {
            encodedFileName = URLEncoder.encode(cloudFilePath, "utf-8");
            String finalUrl = String.format("%s/%s",confQiniu.getDomainofbucket(), encodedFileName);
            savelocalFilePath = urlDownLoadSource(finalUrl, cloudFilePath, localFilePath);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.toString());
        }
        return savelocalFilePath;

    }


    @Override
    public String uploadMultiPartFile(String localFilePath) {
        return null;
    }

    public void downLoadPrivateFile(String fileName, String saveFilePath) throws IOException {
      //  String domainOfBucket = "http://ov6imccl2.bkt.clouddn.com";
        initQiniuClient();
        String encodedFileName = URLEncoder.encode(fileName, "utf-8");
        String privateUrl = String.format("%s/%s",confQiniu.getDomainofbucket(), encodedFileName);

        long expireInSeconds = 3600;//1小时，可以自定义链接过期时间
        String finalUrl = auth.privateDownloadUrl(privateUrl, expireInSeconds);
        urlDownLoadSource(finalUrl,fileName,saveFilePath);
    }

    public void getYunFileInfomation(String yunFileName){
        initQiniuClient();
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            FileInfo fileInfo = bucketManager.stat(confQiniu.getBucket(), yunFileName);
            System.out.println("YunFile hash:"+fileInfo.hash);
            System.out.println("YunFile size:"+fileInfo.fsize);
            System.out.println("YunFile mimeType:"+fileInfo.mimeType);
            System.out.println("YunFile putTime: "+fileInfo.putTime);
        } catch (QiniuException ex) {
            System.err.println(ex.response.toString());
        }
    }

    @Override
    public boolean deleteFile(String cloudFilePath){
        initQiniuClient();
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(confQiniu.getBucket(), cloudFilePath);
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
        }
        return true;
    }


    public static void main(String [] args) throws IOException {
     //   String localFilePath="D:\\Test\\split\\Hadoop，The Definitive Guide.pdf-2.dat";
        QiniuServiceImpl qiniu = new QiniuServiceImpl();
      //  qiniu.randomAcessUpLoadFile(localFilePath);
      //  qiniu.uploadFile(localFilePath);
        String saveFilePath="D:\\Test\\merge";
        String fileName = "split/encrypt/5ebe72ba6a5132cc718ccf11909e10d6.dat";
        String sts = qiniu.downLoadFile(fileName,saveFilePath);
        System.out.println(sts);
  //      qiniu.deleteCloudFile(fileName);
   //     String yunFileName ="README.txt";
      //  qiniu.getYunFileInfomation(yunFileName);
    }
}
