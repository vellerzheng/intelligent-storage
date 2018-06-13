package com.mcloud.storageweb.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: vellerzheng
 * @Description:
 * @Date:Created in 21:00 2018/6/13
 * @Modify By:
 */
@Controller
public class FileManagerController {

    @GetMapping(value = "/filemanager/files/")
    public String  fileList(){
        return "/filemanager/files";
    }
}
