package com.mcloud.fileserver.service.HelloTest;


import com.mcloud.fileserver.service.HelloTest.impl.SchedualServiceHihystric;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * @Author: vellerzheng
 * @Description:
 * @Date:Created in 21:55 2018/5/29
 * @Modify By:
 *
 */

@FeignClient(value="web-provider",fallback = SchedualServiceHihystric.class)
public interface SchedualServiceHello {
    @RequestMapping(value = "/hello")
    String sayHelloFromClientOne(@RequestParam("name") String name);
    @RequestMapping(value = "/hi")
    String sayHiFromClientOne();

}
