package com.mcloud.fileserver.service.file.fileOperate.download;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @Author: vellerzheng
 * @Description:
 * @Date:Created in 15:45 2018/6/6
 * @Modify By:
 */
@Component
public class DownLoadServiceImpl implements DownLoadService{

    private JSONObject jsonObject;

    DownLoadServiceImpl(){

    }

    public DownLoadServiceImpl(JSONObject jsonObject){
        this.jsonObject = jsonObject;
    }

    @Override
    public void DownLoadFile() {

    }
}
