package com.mcloud.storageweb.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: vellerzheng
 * @Description:
 * @Date:Created in 17:09 2018/6/8
 * @Modify By:
 */
public class ConsumerController {

    @RequestMapping(value = "/cloudPath")
    public String getCloudPath(JSONObject jsonObject){

        System.out.println("云文件与文件路径： " + jsonObject.toJSONString());
        return "received";
    }
}
