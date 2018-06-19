package com.mcloud.storageweb.util;


import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;


/**
 * @Author: vellerzheng
 * @Description:
 * @Date:Created in 11:30 2018/6/4
 * @Modify By:
 */
public class CustomFileUtils {
    private static final String WEBADDR = "http://118.31.60.54";


    public static String upload(String httpUrl,String username, File file){
        CloseableHttpClient client = HttpClients.createDefault();

        HttpPost httppost = new HttpPost(httpUrl);

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setCharset(Charset.forName(HTTP.UTF_8));//设置请求的编码格式
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);//设置浏览器兼容模式
        builder.addBinaryBody("uploadfile", file);

        HttpEntity reqEntity = builder.build();

        httppost.setEntity(reqEntity);
        httppost.setHeader("username",username);

        CloseableHttpResponse resp = null;
        String str = null;
        try {
            resp = client.execute(httppost);
            str = EntityUtils.toString(resp.getEntity());
            resp.close();
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;

    }

    public static String generateUniqueFilePath(String userName){
        //保存时的文件名
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar calendar = Calendar.getInstance();
        Random random = new Random();
        return   calendar.get(Calendar.YEAR)+ "/" + (calendar.get(Calendar.MONTH) + 1)
                + "/" + calendar.get(Calendar.DAY_OF_MONTH) + "/" + userName +
               "/" + String.valueOf(random.nextInt(9999));
    }




    public static boolean downLoadFromUrl(String urlStr,String savePath) throws IOException{
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        //设置超时间为3秒
        conn.setConnectTimeout(3*1000);
        //防止屏蔽程序抓取而返回403错误
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

        //得到输入流
        InputStream inputStream = conn.getInputStream();
        //获取自己数组
        byte[] getData = readInputStream(inputStream);

        //文件保存位置
        File saveDir = new File(savePath);
        if(!saveDir.exists()){
            saveDir.mkdir();
        }
        String fileName = urlStr.substring(urlStr.lastIndexOf("/")+1, urlStr.length());
        File file = new File(saveDir+File.separator+fileName);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(getData);
        if(fos!=null){
            fos.close();
        }
        if(inputStream!=null){
            inputStream.close();
        }


        System.out.println("info:"+url+" download success");
        return  true;

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



    public static void main(String[] args) throws Exception {
       String httpUrl = "http://118.31.60.54:8501/file/upload";

        String filePath ="D:\\Test\\split\\云智能运维自动化部署关键技术研究_李志刚.pdf";
        System.out.println(upload(httpUrl,"kraft", new File(filePath)));
/*        String downUrl = "http://118.31.60.54:8500/doc/test.txt";
        String fileName = downUrl.substring(downUrl.lastIndexOf("/")+1, downUrl.length());
        System.out.println(fileName);*/
/*
        boolean res = downLoadFromUrl("http://118.31.60.54:8500/doc/test.txt","D:\\Test\\split");
        System.out.println(res);
*/

    }
}
