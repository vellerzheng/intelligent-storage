package com.mcloud.fileserver.service.file.fileOperate.delete;

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
public class DeleteServiceImpl implements DeleteService {

    private JSONObject jsonObject;

    DeleteServiceImpl(){

    }

    public DeleteServiceImpl(JSONObject jsonObject){
        this.jsonObject = jsonObject;
    }

    @Override
    public void deleteFile() {

    }
}
