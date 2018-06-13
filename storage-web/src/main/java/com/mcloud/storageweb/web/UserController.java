package com.mcloud.storageweb.web;


import com.mcloud.storageweb.common.Const;
import com.mcloud.storageweb.common.aop.LoggerManage;
import com.mcloud.storageweb.repository.entity.User;
import com.mcloud.storageweb.repository.result.ExceptionMsg;
import com.mcloud.storageweb.repository.result.ResponseData;
import com.mcloud.storageweb.service.User.UserService;
import com.mcloud.storageweb.util.BloomFilterUtils;
import com.mcloud.storageweb.util.CustomDateConverter;
import com.mcloud.storageweb.util.InfoJson;
import com.mcloud.storageweb.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;


/**
 * @Author: vellerzheng
 * @Description:
 * @Date:Created in 13:12 2018/6/1
 * @Modify By:
 */
@Controller
public class UserController extends BaseController{
    @Autowired
    private UserService userService;
    @Autowired
    RedisUtil redisUtil;


    @PostMapping("/register")
    public ResponseData register(User user){
        User userSql = userService.selectByUserName(user.getUsername());
        if(userSql != null){
            return new ResponseData(ExceptionMsg.UserNameUsed);
        }
        // 此处省略校验逻辑

        String passward = user.getPassword();
        passward = new BCryptPasswordEncoder().encode(passward);
        user.setPassword(passward);
        user.setCreatetime(CustomDateConverter.currentTime());
        user.setUpdatetime(CustomDateConverter.currentTime());
        userService.insert(user);
        BloomFilterUtils.create(user.getUsername());
        String preUrl ="/welcome";
        userService.setUserRole(user.getUsername(),3);
        return new ResponseData(ExceptionMsg.SUCCESS, preUrl);
    }


    @PostMapping(value = "/login")
    @ResponseBody
    @LoggerManage(description="登陆")
    public InfoJson login(@RequestBody User loginUser) {
        System.out.println("登陆开始");
        try {
            //这里不是bug，前端userName有可能是邮箱也有可能是昵称。
            User user = userService.selectByUserName(loginUser.getUsername());
            if (loginUser == null ) {
                return InfoJson.setFaile("用户不存在");
            }else if(!loginUser.getUsername().equals(user.getUsername())){
                return InfoJson.setFaile("用户名或密码错误");
            }
            Cookie cookie = new Cookie(Const.LOGIN_SESSION_KEY, cookieSign(loginUser.getId().toString()));
            cookie.setMaxAge(Const.COOKIE_TIMEOUT);
            cookie.setPath("/");
           // response.addCookie(cookie);
            getSession().setAttribute(Const.LOGIN_SESSION_KEY, loginUser);
            String preUrl = "/";
            System.out.println("登陆成功");
            return InfoJson.getSucc( preUrl);
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("login failed, ", e);
            return InfoJson.setFaile("登陆失败");
        }
    }




/*    @Autowired
    private MqttService mqttService;*/

    @RequestMapping(value = "/user", produces = {"application/json;charset=UTF-8"})
    public User addUser(User user){


//        try {
//            mqttService.publish("Hello","1234567889----------------------------------------------");
//        } catch (MqttException | UnsupportedEncodingException | InterruptedException e) {
//            e.printStackTrace();
//        }
//        mqttService.runListenReceive("hello");

        User usr = userService.selectByPrimaryKey(user.getId());

        redisUtil.set(usr.getUsername(),usr.getEmail());
        String email = (String)redisUtil.get(usr.getUsername());
        System.out.println("test redis get : " +email);
        redisUtil.set(usr.getUsername(),(long)120039,usr);
        User user2 = (User)redisUtil.get(usr.getUsername());
        redisUtil.remove(usr.getUsername());

        return usr;
    }
}
