package com.mcloud.storageweb.controller;


import com.alibaba.fastjson.JSON;
import com.mcloud.storageweb.repository.entity.FileHash;
import com.mcloud.storageweb.service.file.FileHashService;
import com.mcloud.storageweb.util.CustomDateConverter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: vellerzheng
 * @Description:  访问 http://localhost:8765/swagger-ui.html 查看api
 * @Date:Created in 17:09 2018/6/8
 * @Modify By:
 */
@Api(value = "/v1", description = "文件交互操作", tags ={ "文件操作"} )
@RestController
@RequestMapping("/v1")
public class FileController {
    @Autowired
    FileHashService fileHashService;

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
        return "received";
    }
}
