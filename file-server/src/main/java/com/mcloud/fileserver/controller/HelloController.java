package com.mcloud.fileserver.controller;


import com.mcloud.fileserver.service.HelloTest.SchedualServiceHello;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: vellerzheng
 * @Description:
 * @Date:Created in 22:01 2018/5/29
 * @Modify By:
 */
@RestController
public class HelloController {
    @Autowired
    SchedualServiceHello schedualService;
    @RequestMapping(value = "/hello")
    public String sayHello(@RequestParam("name") String name){
        return schedualService.sayHelloFromClientOne(name);
    }

    @RequestMapping(value = "/hi")
    public String sayhi(){
        return schedualService.sayHiFromClientOne();
    }
}

