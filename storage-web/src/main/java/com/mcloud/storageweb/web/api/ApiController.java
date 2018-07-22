package com.mcloud.storageweb.web.api;


import com.alibaba.fastjson.JSON;
import com.mcloud.storageweb.repository.entity.FileEntity;
import com.mcloud.storageweb.repository.entity.FileHash;
import com.mcloud.storageweb.service.file.FileHashService;
import com.mcloud.storageweb.service.file.FileService;
import com.mcloud.storageweb.util.CustomDateConverter;
import com.mcloud.storageweb.util.CustomFileUtils;
import com.mcloud.storageweb.util.FileManage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;

/**
 * @Author: vellerzheng
 * @Description:  访问 http://localhost:8765/swagger-ui.html 查看api
 * @Date:Created in 17:09 2018/6/8
 * @Modify By:
 */
@Api(value = "/api/v1", description = "文件交互操作", tags ={ "文件操作"} )
@RestController
@RequestMapping("/api/v1")
public class ApiController {
    @Autowired
    FileHashService fileHashService;
    @Autowired
    FileService fileService;

    @ApiOperation(value = "获取云文件路径",notes = "获取文件路径")
    @ApiImplicitParams({@ApiImplicitParam( name = "fileHash",value="FileHash",required = true,dataType = "FileHash"),
            @ApiImplicitParam(name="fileId",value="FileId", required = true,dataType = "Integer"),
            @ApiImplicitParam(name = "userId",value = "userId",required = true, dataType = "Integer")
                        })

    @RequestMapping(value = "/cloudPath")
    public String insertCloudPath(@RequestBody FileHash fileHash){
        fileHash.setCreatetime(CustomDateConverter.currentTime());
        fileHashService.insert(fileHash);
        System.out.println("云文件与文件路径： " + JSON.toJSONString(fileHash));
    //    FileEntity fileEntity = fileService.selectByPrimaryKey(fileHash.getId());
        return "received";
    }

    @RequestMapping(value = "/downloadResult")
    public   ResponseEntity<byte[]>  downloadResult(@RequestBody FileEntity fileEntity) throws IOException {

        String downloadFilePath = fileEntity.getFilePath();
        String urlstr = "http://118.31.60.54:8500" + downloadFilePath;

        FileEntity sourceFileEntity = fileService.selectByPrimaryKey(fileEntity.getId());
        String tempFile = "./tempFile" +"/" + sourceFileEntity.getFileName();
        File downLoadNewFile = new File(tempFile);
        //判断路径是否存在，如果不存在就创建一个
        if (!downLoadNewFile.getParentFile().exists()) {
            downLoadNewFile.getParentFile().mkdirs();
        }
        CustomFileUtils.downLoadFromUrl(urlstr, tempFile);

        /* 将文件下载下来*/
        HttpHeaders headers = new HttpHeaders();
        //下载显示的文件名，解决中文名称乱码问题
        String downloadFileName = new String(fileEntity.getFileName().getBytes("UTF-8"),"iso-8859-1");
        //通知浏览器以attachment（下载方式）打开文件
        headers.setContentDispositionFormData("attachment", downloadFileName);
        /*application/octet-stream ： 二进制流数据（最常见的文件下载）*/
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        ResponseEntity<byte[]> resEty = new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(downLoadNewFile),
                headers, HttpStatus.OK);

        /*判断路径是否存在，如果存在就删除*/
       if (downLoadNewFile.exists()) {
            FileManage.deleteFile(tempFile);
        }

        return resEty;
    }
}
