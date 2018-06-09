package com.mcloud.storageweb.controller;


import com.mcloud.storageweb.repository.entity.User;
import com.mcloud.storageweb.service.User.UserService;
import com.mcloud.storageweb.service.file.FileService;
import com.mcloud.storageweb.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
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
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    FileService fileService;
/*    @Autowired
    private MqttService mqttService;*/

    @RequestMapping(value = "/user", produces = {"application/json;charset=UTF-8"})
    public User addUser(User user){

        String filePath ="D:\\Test\\split\\分布式网络架构.pdf";
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
        redisUtil.set(usr.getUsername(),(long)120039,usr);
        User user2 = (User)redisUtil.get(usr.getUsername());
        redisUtil.remove(usr.getUsername());

        fileService.tranferEventToFileServer(usr,209, filePath);

        return usr;
    }
}
