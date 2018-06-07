package com.mcloud.fileserver.service.file.fileOperate.upload;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.mcloud.fileserver.util.FileManage;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @Author: vellerzheng
 * @Description:
 * @Date:Created in 15:45 2018/6/6
 * @Modify By:
 */

@Component
public class UploadServiceImpl implements UploadService {

    private JSONObject jsonObject;

    UploadServiceImpl(){

    }

    public UploadServiceImpl(JSONObject jsonObject){
        this.jsonObject = jsonObject;
    }

    @Override
    public void uploadFile() {

        System.out.println("=========================================="+jsonObject.toJSONString());
    }

    public boolean handleFile(){
        //重命名
        String filePath = jsonObject.getString("filePath");
        Integer sliceNum = jsonObject.getInteger("id");
        String fileSuffix = filePath.substring(filePath.lastIndexOf(".")+1);

        String fileMd5 = FileManage.getMD5ByFile(filePath);
        //切片

        // 多线程加密

        return  true;
    }

}
