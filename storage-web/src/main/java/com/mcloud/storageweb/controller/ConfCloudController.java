package com.mcloud.storageweb.controller;

import com.mcloud.storageweb.repository.entity.*;
import com.mcloud.storageweb.service.cloudConf.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: vellerzheng
 * @Description:
 * @Date:Created in 19:14 2018/6/3
 * @Modify By:
 */
@RestController
@RequestMapping(value = "/conf")
public class ConfCloudController {
    @Autowired
    ConfAliyunService confAliyunService;
    @Autowired
    ConfNeteaseService confNeteaseService;
    @Autowired
    ConfQcloudService confQcloudService;
    @Autowired
    ConfQiniuService confQiniuService;
    @Autowired
    ConfUpyunService confUpyunService;

    @RequestMapping(value = "/aliyun")
    public ConfAliyun confAliyun(@RequestParam("userId") Integer userId){
        return confAliyunService.selectByUserIdAndStatus(userId);

    }

    @RequestMapping(value = "/netease")
    public ConfNetease confNetease(@RequestParam("userId")Integer userId){
        return confNeteaseService.selectByUserIdAndStatus(userId);
    }

    @RequestMapping(value = "/qcloud")
    public ConfQcloud confQcloud(@RequestParam("userId")Integer userId){
        return confQcloudService.selectByUserIdAndStatus(userId);
    }

    @RequestMapping(value = "/qiniu")
    public ConfQiniu confQiniu(@RequestParam("userId") Integer userId){
        return confQiniuService.selectByUserIdAndStatus(userId);
    }

    @RequestMapping(value = "/upyun")
    public ConfUpyun confUpyun(@RequestParam("userId") Integer userId){
        return confUpyunService.selectByUserIdAndStatus(userId);
    }


}
