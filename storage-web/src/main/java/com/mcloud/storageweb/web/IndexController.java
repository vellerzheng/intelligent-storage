package com.mcloud.storageweb.web;


import com.mcloud.storageweb.repository.entity.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

/**
 * @Author: vellerzheng
 * @Description:
 * @Date:Created in 11:02 2018/6/12
 * @Modify By:
 */
@Controller
public class IndexController {

    @RequestMapping(value = {"/","/index"})
    public String index(){
        return "index";
    }

    @RequestMapping(value = "/login")
    public String login(){
        return "login";
    }

    @RequestMapping(value = "/logout")
    public String logout(){
        return "logout";
    }

    @GetMapping(value = "/register")
    public String register(){
        return "register";
    }

    @GetMapping("/user")
    public String user(@AuthenticationPrincipal Principal principal, Model model){
        model.addAttribute("username", principal.getName());
        return "user/user";
    }

    @GetMapping(value = "/welcome")
    public String welcom(@AuthenticationPrincipal Principal principal, Model model){
        model.addAttribute("username",principal.getName());
        return "welcome";
    }

    @RequestMapping(value = "/cloudConf/aliyunConfig")
    public String aliyunConfig(@AuthenticationPrincipal Principal principal,Model model){
        model.addAttribute("username",principal.getName());
        return "cloudConf/aliyunConfig";
    }

    @RequestMapping(value = "/cloudConf/neteaseConfig")
    public String neteaseConfig(@AuthenticationPrincipal Principal principal,Model model){
        model.addAttribute("username",principal.getName());
        return "cloudConf/neteaseConfig";
    }
    @RequestMapping(value = "/cloudConf/qCloudConfig")
    public String qCloudConfig(@AuthenticationPrincipal Principal principal,Model model){
        model.addAttribute("username",principal.getName());
        return "cloudConf/qCloudConfig";
    }
    @RequestMapping(value = "/cloudConf/qiniuConfig")
    public String qiniuConfig(@AuthenticationPrincipal Principal principal,Model model){
        model.addAttribute("username",principal.getName());
        return "cloudConf/qiniuConfig";
    }
    @RequestMapping(value = "/cloudConf/upyunConfig")
    public String upyunConfig(@AuthenticationPrincipal Principal principal,Model model){
        model.addAttribute("username",principal.getName());
        return "cloudConf/upyunConfig";
    }
}
