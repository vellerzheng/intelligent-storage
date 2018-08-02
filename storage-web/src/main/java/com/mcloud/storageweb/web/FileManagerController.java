package com.mcloud.storageweb.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mcloud.storageweb.common.constant.FileConst;
import com.mcloud.storageweb.repository.entity.*;
import com.mcloud.storageweb.repository.entity.common.ConfCloud;
import com.mcloud.storageweb.service.User.UserService;
import com.mcloud.storageweb.service.file.FileHashService;
import com.mcloud.storageweb.service.file.FileOperateService;
import com.mcloud.storageweb.service.file.FileService;
import com.mcloud.storageweb.service.file.SchedualFileService;
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
    FileHashService fileHashService;
    @Autowired
    FileOperateService fileOperateService;
    @Autowired
    SchedualFileService schedualFileService;

    @RequestMapping(value = "/files/")
    public String fileList(@RequestParam("username") String username, ModelMap modelMap,
                                 @RequestParam(name = "pageNum", defaultValue="1") Integer pageNum,
                                 @RequestParam(name= "pageSize", defaultValue="5") Integer pageSize){


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
                String saveName = "";
                if(fileName!= null && fileName.contains(".")) {
                     saveName = UUID.randomUUID().toString() + fileName.substring(fileName.lastIndexOf("."));
                }
                String httpUrl = "http://118.31.60.54:8501/file/upload";
                File temFile = new File(saveName);
                FileUtils.copyInputStreamToFile(file.getInputStream(),temFile);
                String jsonInfo = CustomFileUtils.upload(httpUrl,username,temFile);
                temFile.delete();

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


    @GetMapping(value = "/downloadfile/")
    public String getDownloadResult(@RequestParam("username") String username, ModelMap modelMap){
        User user= userService.selectByUserName(username);
        modelMap.addAttribute("username",user.getUsername());
        modelMap.addAttribute("loginUser",user);
        return "filemanager/downloadfile";
    }

    @RequestMapping(value = "/downloadfile/download")
    @ResponseBody
    public  InfoJson downloadFile(@RequestParam("username")String username,@RequestParam("fileId") Integer fileId){
        User user = userService.selectByUserName(username);
        FileEntity fileEntity = fileService.selectByPrimaryKey(fileId);


        ConfCloud confCloud = fileOperateService.prepareCloudConfig(user.getId(),fileId);

        InfoJson infoJson = schedualFileService.downLoadFile(confCloud,user.getId(),user.getUsername(),fileEntity.getFileName(),fileEntity.getFilePath());

        //     fileOperateService.downLoadFile(usr,fileEntity.getId(), fileEntity.getFilePath());
        return InfoJson.getSucc("下载任务已经提交！");
    }

    @RequestMapping(value = "/deleteFile/")
    @ResponseBody
    public InfoJson deleteFile(@RequestParam("username")String username,@RequestParam("fileId") Integer fileId){

        User usr = userService.selectByUserName(username);
        FileEntity fileEntity = fileService.selectByPrimaryKey(fileId);
        fileOperateService.deleteFile(usr,fileEntity.getId(),fileEntity.getFilePath());
        return InfoJson.getSucc("删除完成！");
    }
}
