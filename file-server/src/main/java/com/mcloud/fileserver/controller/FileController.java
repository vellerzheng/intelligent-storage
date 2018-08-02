package com.mcloud.fileserver.controller;

import com.alibaba.fastjson.JSONObject;

import com.mcloud.fileserver.repository.entity.ConfCloud;
import com.mcloud.fileserver.repository.entity.FileEntity;
import com.mcloud.fileserver.repository.entity.FileHash;
import com.mcloud.fileserver.repository.entity.User;
import com.mcloud.fileserver.service.file.fileOperate.download.DownLoadService;
import com.mcloud.fileserver.service.infoExchange.CloudFilePathService;
import com.mcloud.fileserver.util.InfoJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
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
@RequestMapping("/api/v1")
public class FileController {

    @Autowired
    DownLoadService downLoadService;
    @Autowired
    CloudFilePathService cloudFilePathService;
    /**
     *
     * @param userId  用户id
     * @param userName 用户名
     * @param filePath 文件路径
     * @param map   <k,v> k云名称，v云配置对象
     * @return    包括用户id,file_hash, 文件切片后的hash。
     */
    @RequestMapping(value = "/uploadFile")
    public InfoJson uploadFile(@RequestParam("userId")Integer userId, @RequestParam("userName")String userName,
                               @RequestParam("filePath")String filePath, @RequestParam("cloudConfig")Map<String,Object> map){
        Map<String,String> result = new HashMap<>();

        return InfoJson.getSucc(result);

    }

    /**
     *
     * @param confCloud  云配置
     * @param fileName   真实文件名
     * @param filePath  文件本地路径
     * @return
     */
    @RequestMapping(value = "/downloadFile")
    public InfoJson downLoadFile(@RequestBody ConfCloud confCloud, @RequestParam("userId")Integer userId,
                                 @RequestParam("username") String userName, @RequestParam("fileName")String fileName,
                                 @RequestParam("filePath")String filePath){
        FileEntity fileEntity = downLoadService.downLoadFile(confCloud,userId,userName,fileName,filePath);
        return InfoJson.getSucc(fileEntity);

    }

    @RequestMapping(value = "/cloudPath")
    public String updateCloudPath(FileHash fileHash){
        return cloudFilePathService.updateCloudPathToLocal(fileHash);
    }
}
