package com.mcloud.dbprovider.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: vellerzheng
 * @Description:
 * @Date:Created in 19:17 2018/5/29
 * @Modify By:
 */
@RestController
public class HelloController {
    @Value("${server.port}")
    String port;

    @RequestMapping("/hi")
    public String hello() {
        return "This is a Eureka client, from port: " + port;
    }

    @RequestMapping("/hello")
    public String home(@RequestParam("name") String name) {
        return "hello "+name+",i am from port:" +port;
    }
}
