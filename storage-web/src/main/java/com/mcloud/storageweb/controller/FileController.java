package com.mcloud.storageweb.controller;

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
public class FileController {
    @Autowired
    FileOperateService fileOperateService;
    @Autowired
    UserService userService;
    @Autowired
    FileHashService fileHashService;



    @RequestMapping(value = "/uploadFile")
    public String uploadFile(){
        String filePath ="D:\\Test\\split\\云智能运维自动化部署关键技术研究_李志刚.pdf";
        User usr = userService.selectByPrimaryKey(3);
        fileOperateService.uploadFile(usr,208, filePath);

        return  "uploadFile ok";
    }

    @RequestMapping(value = "/downloadFile")
    public  String downloadFile(){

        String filePath ="D:\\Test\\merge";
        User usr = userService.selectByPrimaryKey(3);
        fileOperateService.downLoadFile(usr,208, filePath);

        return "downLoadFile Ok";
    }

    @RequestMapping(value = "/deleteFile")
    public String deleteFile(){

        String filePath ="D:\\Test\\merge";
        User usr = userService.selectByPrimaryKey(3);
        fileOperateService.deleteFile(usr,208,filePath);
        return "delete File ok!";
    }
}
