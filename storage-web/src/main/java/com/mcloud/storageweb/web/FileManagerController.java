package com.mcloud.storageweb.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mcloud.storageweb.common.constant.FileConst;
import com.mcloud.storageweb.repository.entity.FileEntity;
import com.mcloud.storageweb.repository.entity.User;
import com.mcloud.storageweb.service.User.UserService;
import com.mcloud.storageweb.service.file.FileOperateService;
import com.mcloud.storageweb.service.file.FileService;
import com.mcloud.storageweb.util.CustomDateConverter;
import com.mcloud.storageweb.util.CustomFileUtils;
import com.mcloud.storageweb.util.InfoJson;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

/**
 * @Author: vellerzheng
 * @Description:
 * @Date:Created in 21:00 2018/6/13
 * @Modify By:
 */
@Controller
@RequestMapping(value = "/filemanager")
public class FileManagerController {
    @Autowired
    UserService userService;
    @Autowired
    FileService fileService;

    @Autowired
    FileOperateService fileOperateService;

    @RequestMapping(value = "/files/")
    public String fileList(@RequestParam("username") String username, ModelMap modelMap,
                                 @RequestParam(name = "pageNum", defaultValue="1") Integer pageNum,
                                 @RequestParam(name="pageSize", defaultValue="5") Integer pageSize){


        User loginUser =userService.selectByUserName(username);
        List<FileEntity> fileList = fileService.selectListByUserId(loginUser.getId());

        PageHelper.startPage(pageNum, pageSize);
        PageInfo<FileEntity> pageInfo = new PageInfo<FileEntity>(fileList);


        modelMap.addAttribute("page",pageInfo);
        modelMap.addAttribute("fileList",fileList);
        modelMap.addAttribute("username",loginUser.getUsername());
        return "filemanager/files";
    }

    @GetMapping(value = "/uploadfile/")
    public String getUploadfile(@RequestParam("username") String username, ModelMap modelMap){
        User user= userService.selectByUserName(username);
        modelMap.addAttribute("username",user.getUsername());
        modelMap.addAttribute("loginUser",user);
        return "filemanager/uploadfile";
    }

    @PostMapping(value = "/uploadfile/add/")
    @ResponseBody
    public InfoJson doUploadFile(@RequestParam("file") List<MultipartFile> fileList,
                                 @RequestParam("description") String description, @RequestParam("username")String username){

        User user= userService.selectByUserName(username);
        try {
            for (MultipartFile file : fileList) {
                String fileName = file.getOriginalFilename();
                String saveName = UUID.randomUUID().toString() + fileName.substring(fileName.lastIndexOf("."));
                String httpUrl = "http://118.31.60.54:8501/file/upload";
                File temFile = new File(saveName);
                FileUtils.copyInputStreamToFile(file.getInputStream(),temFile);
                String jsonInfo = CustomFileUtils.upload(httpUrl,username,temFile);


                FileEntity fileEntity = new FileEntity();
                fileEntity.setUserId(user.getId());
                fileEntity.setFileName(fileName);
                fileEntity.setDescription(description);
                fileEntity.setStatus(FileConst.UPLOAD_HANDLING);
                fileEntity.setSize(file.getSize()+"");
                fileEntity.setVersion(1);
                fileEntity.setCreatetime(CustomDateConverter.currentTime());
                fileService.insert(fileEntity);

                JSONObject jsonObject = JSON.parseObject(jsonInfo);
                fileEntity.setFilePath(jsonObject.getString("filePath"));
                if(jsonObject.getInteger("status")==1)
                fileEntity.setStatus(1);
                fileService.updateByPrimaryKeySelective(fileEntity);
                fileOperateService.uploadFile(user,fileEntity.getId(),fileEntity.getFilePath());

            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return InfoJson.getSucc("上传成功");
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
