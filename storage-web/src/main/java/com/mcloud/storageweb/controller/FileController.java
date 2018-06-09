package com.mcloud.storageweb.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @ApiOperation(value = "获取云文件路径",notes = "获取文件路径")
    @ApiImplicitParam(name = "object",value="Object",required = true,dataType = "Object")
    @RequestMapping(value = "/cloudPath")
    public String getCloudPath(@RequestBody  Object object){

        System.out.println("云文件与文件路径： " + object.toString());
        return "received";
    }
}
