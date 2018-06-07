package com.mcloud.fileserver.service.HelloTest.impl;


import com.mcloud.fileserver.service.HelloTest.SchedualServiceHello;
import org.springframework.stereotype.Component;

/**
 * @Author: vellerzheng
 * @Description:
 * @Date:Created in 11:20 2018/5/30
 * @Modify By:
 */
@Component
public class SchedualServiceHihystric implements SchedualServiceHello {
    @Override
    public String sayHelloFromClientOne(String name) {
        return "Sorry" +name;
    }

    @Override
    public String sayHiFromClientOne() {
        return "Sorry!";
    }


}
