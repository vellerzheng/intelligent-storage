package com.mcloud.storageweb.service.file.Impl;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import com.mcloud.storageweb.repository.entity.common.FileInfo;
import com.mcloud.storageweb.service.file.UploadFileService;
import com.mcloud.storageweb.util.CustomDateConverter;
import com.mcloud.storageweb.util.MD5Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.PeekingIterator;



/**
 *
 * @author zelei.fan
 *
 */
@Service
public class UploadFileServiceImpl implements UploadFileService {

 //   @Value("${upload.file.url}")
    private String uploadUrl;

//    @Value("webdav")
    private String uploadUsername;

 //   @Value("webdav")
    public String uploadPassword;

    @Override
    public FileInfo uploadFile(MultipartFile multipartFile) {

        //取到文件大小，如果超过指定范围的话就直接返回提醒错误
        long size = multipartFile.getSize();
        //获取文件名
        String fileName = multipartFile.getOriginalFilename();
        // 获取文件后缀，即文件类型
        String fileExt = "";
        if (fileName.contains(".")) {
            fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        }
        //设置MD5加密
        String fileMD5 = md5File(multipartFile);

        //拼接文件路径:/后缀/年/月/日/md5/filename
        String saveUrl = "/" + fileExt + new SimpleDateFormat("/yyyy/MM/dd/").format(new Date()) + fileMD5 + "/" + multipartFile.getOriginalFilename();

        String location = null;
        try {
            location = saveFile(multipartFile, saveUrl);//保存文件操作
        } catch (Exception e) {
            e.printStackTrace();
        }

        FileInfo fileInfo = new FileInfo();
        fileInfo.setAbsoluteUrl(location);
        fileInfo.setRelativeUrl(saveUrl);
        fileInfo.setFileMd5(fileMD5);
        fileInfo.setFileName(fileName);
        fileInfo.setFileSize(String.valueOf(size));
        fileInfo.setFileType(fileExt);
        fileInfo.setCreateAt(CustomDateConverter.currentTime().toString());
        return fileInfo;

    }

    //MD5加密
    private String md5File(MultipartFile multipartFile) {
        try {
            return MD5Utils.fileMD5(multipartFile.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



    private String saveFile(MultipartFile file, String savePath) throws Exception {
        // 上传文件，到文件服务器，uploadUrl是在config中配好的，就是给uploadUrl赋值，如果不用那么麻烦的话可以直接把url放进去:upload("http://192.168.102.11/test", uploadUsername, uploadPassword, savePath, file.getBytes());
  //      upload(uploadUrl, uploadUsername/*那台服务器的用户名*/, uploadPassword/*那台服务器的密码*/, savePath, file.getBytes());
        return append(uploadUrl, savePath);

    }


/*    public static void upload(String webDavServer, String webDavUser, String webDavPassword, String remotePath, byte[] bytes) throws IOException {

        if (!webDavServer.endsWith("/")) webDavServer += "/";

        //连接服务器
        HttpURL hrl = new HttpURL(webDavServer);
        hrl.setUserinfo(webDavUser, webDavPassword);

        WebdavResource wdr =  new WebdavResource(hrl);

        //make directory if need
        StringBuffer ssdir = new StringBuffer();
        // 去除最后一个文件名
        StringTokenizer t = new StringTokenizer(remotePath.substring(0, remotePath.lastIndexOf("/")), "/");
        while(t.hasMoreTokens()){
            String sdir = t.nextToken();
            ssdir.append(sdir+"/");
            wdr.mkcolMethod(wdr.getPath() + ssdir );
        }

        String remoteFile= wdr.getPath() + remotePath;//拼成绝对地址
        boolean result = wdr.putMethod(remoteFile, bytes);//把文件写进去
        checkArgument(result, "文件上传出错");//false时会报错，true则为成功

        wdr.close();//最后关闭连接

    }*/



    /**
     * 连接 URL
     * @param paths
     * @return
     */
    public static String append(String... paths) {
        List<String> pathList = Lists.newArrayList(paths);
        PeekingIterator<String> iter = Iterators.peekingIterator(pathList.iterator());
        StringBuilder urlBuilder = new StringBuilder();
        while (iter.hasNext()) {
            String current = iter.next();
            urlBuilder.append(current);
            if (!iter.hasNext()) {
                break;
            }
            if (current.endsWith("/") && iter.peek().startsWith("/")) {
                urlBuilder.deleteCharAt(urlBuilder.length() - 1);
            } else if (!current.endsWith("/") && !iter.peek().startsWith("/")) {
                urlBuilder.append("/");
            }
        }
        return urlBuilder.toString();
    }

}
