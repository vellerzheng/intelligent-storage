package com.mcloud.fileserver.controller;

import com.mcloud.fileserver.repository.entity.User;
import com.mcloud.fileserver.service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: vellerzheng
 * @Description:
 * @Date:Created in 20:05 2018/6/1
 * @Modify By:
 */
@RestController
public class UserController {
    @Autowired
    UserService userService;
    @RequestMapping(value = "/user")
    public User sayHello(@RequestParam("id") Integer id){
            User user = userService.getUser(id);
            return user;

    }

}
