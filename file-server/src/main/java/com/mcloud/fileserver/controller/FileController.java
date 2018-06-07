package com.mcloud.fileserver.controller;

import com.mcloud.fileserver.service.cloud.AliyunService;
import com.mcloud.fileserver.service.cloud.impl.AliyunServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: vellerzheng
 * @Description:
 * @Date:Created in 20:15 2018/6/3
 * @Modify By:
 */
@RestController
public class FileController {

    /**
     *
     * @param userId  用户id
     * @param userName 用户名
     * @param filePath 文件路径
     * @param map   <k,v> k云名称，v云配置对象
     * @return    包括用户id,file_hash, 文件切片后的hash。
     */
    @RequestMapping(value = "/uploadFile")
    public Map<String,String> uploadFile(@RequestParam("userId")Integer userId, @RequestParam("userName")String userName,
                      @RequestParam("filePath")String filePath,@RequestParam("cloudConfig")Map<String,Object> map){
        Map<String,String> result = new HashMap<>();
        return result;

    }
}
