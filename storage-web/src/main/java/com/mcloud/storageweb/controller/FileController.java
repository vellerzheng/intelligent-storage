package com.mcloud.storageweb.controller;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@Api(value = "文件controller", description = "文件交互操作", tags ={ "文件操作"} )
@RestController
public class FileController {

    @ApiOperation("获取云文件路径")
    @RequestMapping(value = "/cloudPath")
    public String getCloudPath(@RequestBody  @ApiParam(name = "云文件路径对象",value = "传入json对象",required = true) Object object){

        System.out.println("云文件与文件路径： " + object.toString());
        return "received";
    }
}
