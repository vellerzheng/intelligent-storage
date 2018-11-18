package com.mcloud.storageweb.web;


import com.mcloud.storageweb.common.Const;
import com.mcloud.storageweb.common.aop.LoggerManage;
import com.mcloud.storageweb.repository.entity.User;
import com.mcloud.storageweb.repository.entity.common.LoginUser;
import com.mcloud.storageweb.repository.result.ExceptionMsg;
import com.mcloud.storageweb.repository.result.ResponseData;
import com.mcloud.storageweb.service.User.UserService;
import com.mcloud.storageweb.util.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.*;
import java.awt.image.BufferedImage;
import java.io.IOException;


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


    @PostMapping(value = "/register")
    @ResponseBody
    public InfoJson register(@RequestBody User user){
        User userSql = userService.selectByUserName(user.getUsername());
        if(userSql != null){
            return InfoJson.setFaile("用户已经存在！");
        }
        // 此处省略校验逻辑

        String passward = user.getPassword();
        passward = new BCryptPasswordEncoder().encode(passward);
        user.setPassword(passward);
        user.setCreatetime(CustomDateConverter.currentTime());
        user.setUpdatetime(CustomDateConverter.currentTime());
        userService.insert(user);
        BloomFilterUtils.create(user.getUsername());
        String preUrl ="/login";
        userService.setUserRole(user.getUsername(),3);
        return InfoJson.getSucc(preUrl);
    }


    @PostMapping(value = "/login", consumes ={"application/json;charset=UTF-8"})
    @ResponseBody
    @LoggerManage(description="登录")
    public InfoJson login(@RequestBody User loginUser,HttpServletRequest request) {
        System.out.println("登陆开始");
        try {
            //这里不是bug，前端userName有可能是邮箱也有可能是昵称。
            User user = userService.selectByUserName(loginUser.getUsername());
            if (user == null ) {
                return InfoJson.setFaile("用户不存在");
            }else if(!loginUser.getUsername().equals(user.getUsername())){
                return InfoJson.setFaile("用户名或密码错误");
            }
            //验证图片
            String code = request.getParameter("code");
            HttpSession session = request.getSession();
            String sessionCode = (String)session.getAttribute("code");
            if(!StringUtils.equalsIgnoreCase(code,sessionCode)){
                throw  new RuntimeException("验证码对应不上code= " + code + " sessioncode =" + sessionCode);

            }

            //设置Cookie
            Cookie cookie = new Cookie(Const.LOGIN_SESSION_KEY, cookieSign(user.getId().toString()));
            cookie.setMaxAge(Const.COOKIE_TIMEOUT);
            cookie.setPath("/");
           // response.addCookie(cookie);
            getSession().setAttribute(Const.LOGIN_SESSION_KEY, user);
            String preUrl = "/welcome";
            System.out.println("登陆成功");
            return InfoJson.getSucc( preUrl);
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("login failed, ", e);
            return InfoJson.setFaile("登陆失败");
        }
    }


    /**
     * 响应验证码页面
     * @return
     */
    @RequestMapping(value="/img/validateCode")
    public String validateCode(HttpServletRequest request,HttpServletResponse response) throws Exception{
        // 设置响应的类型格式为图片格式
        response.setContentType("image/jpeg");
        //禁止图像缓存。
        response.setHeader("Pragma", "-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        HttpSession session = request.getSession();

        ValidateCode vCode = new ValidateCode(120,40,4,100);
        session.setAttribute("code", vCode.getCode());
        vCode.write(response.getOutputStream());
        return null;
    }


  /*
   *
   * 自动验证验证码
   * */
   @RequestMapping(value = "/verificateCodeServlet")
   public void verficateImageCode(HttpServletRequest request, HttpServletResponse response) {
       //验证图片
       String code = request.getParameter("verificationCode");
       HttpSession session = request.getSession();
       String sessionCode = (String)session.getAttribute("code");
       if(!StringUtils.equalsIgnoreCase(code,sessionCode)){
           throw  new RuntimeException("验证码对应不上code= " + code + " sessioncode =" + sessionCode);

       }

       response.setContentType("text/plain;charset=utf-8");

       ServletOutputStream outputStream = null;
       try {
           outputStream = response.getOutputStream();
           outputStream.write("verify image Success!".getBytes("utf-8"));
           outputStream.flush();
           outputStream.close();
       } catch (IOException e) {
           e.printStackTrace();
       }

   }






    @RequestMapping(value = "/user", produces = {"application/json;charset=UTF-8"})
    public User addUser(User user){


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
