package com.mcloud.storageweb.web.api;

import com.mcloud.storageweb.repository.entity.FileHash;
import com.mcloud.storageweb.repository.entity.User;
import com.mcloud.storageweb.service.User.UserService;
import com.mcloud.storageweb.service.file.FileHashService;
import com.mcloud.storageweb.service.file.FileOperateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: vellerzheng
 * @Description:
 * @Date:Created in 11:24 2018/6/11
 * @Modify By:
 */
@RestController
@RequestMapping(value = "/test")
public class FileTestController {
    @Autowired
    FileOperateService fileOperateService;
    @Autowired
    UserService userService;
    @Autowired
    FileHashService fileHashService;



    @RequestMapping(value = "/uploadFile")
    public String uploadFile(){
        String filePath ="D:\\Test\\split\\分布式网络架构.pdf";
        User usr = userService.selectByPrimaryKey(23);
        fileOperateService.uploadFile(usr,263, filePath);

        return  "uploadFile ok";
    }

    @RequestMapping(value = "/downloadFile")
    public  String downloadFile(){

        String filePath ="D:\\Test\\merge";
        User usr = userService.selectByPrimaryKey(23);
        fileOperateService.downLoadFile(usr,250, filePath);

        return "downLoadFile Ok";
    }

    @RequestMapping(value = "/deleteFile")
    public String deleteFile(){

        String filePath ="D:\\Test\\merge";
        User usr = userService.selectByPrimaryKey(23);
        fileOperateService.deleteFile(usr,208,filePath);
        return "delete File ok!";
    }
}
