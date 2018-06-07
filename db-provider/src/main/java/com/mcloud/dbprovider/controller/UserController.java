package com.mcloud.dbprovider.controller;

import com.mcloud.dbprovider.repository.entity.User;
import com.mcloud.dbprovider.service.User.UserService;
import com.mcloud.dbprovider.util.InfoJson;
import com.mcloud.dbprovider.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Author: vellerzheng
 * @Description:
 * @Date:Created in 13:12 2018/6/1
 * @Modify By:
 */
@RestController
public class UserController {
    @Autowired
    private UserService userService;
/*    @Autowired
    RedisUtil redisUtil;*/
/*    @Autowired
    private MqttService mqttService;*/

    @RequestMapping(value = "/user", produces = {"application/json;charset=UTF-8"})
    public User addUser(User user){
     /*   try {
            mqttService.publish("Hello","1234567889----------------------------------------------");
        } catch (MqttException | UnsupportedEncodingException | InterruptedException e) {
            e.printStackTrace();
        }
        mqttService.runListenReceive("hello");*/

        User usr = userService.selectByPrimaryKey(user.getId());

 /*       redisUtil.set(usr.getUsername(),usr.getEmail());
        String email = (String)redisUtil.get(usr.getUsername());
        System.out.println("test redis get : " +email);*/
  //      redisUtil.setEx(usr.getUsername(),120039,usr);
  //      Object user2 = redisUtil.get(usr.getUsername());

   //     redisUtil.del(usr.getUsername());

        return usr;
    }
}
