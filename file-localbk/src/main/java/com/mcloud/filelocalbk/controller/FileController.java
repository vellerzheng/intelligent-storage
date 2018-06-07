package com.mcloud.filelocalbk.controller;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mcloud.filelocalbk.util.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: vellerzheng
 * @Description:
 * @Date:Created in 13:10 2018/6/4
 * @Modify By:
 */
@RestController
@RequestMapping(value = "/file")
public class FileController {
    //private final static String FILE_PATH_DIR="D:\\Test\\merge";
    private final static String FILE_PATH_DIR="/opt/data/fileShareDir";

    @RequestMapping(value="/upload", method=RequestMethod.POST)
    public JSONObject upload(HttpServletRequest request)
    {
        JSONObject json = new JSONObject();
        Part part = null;
        String username = null;
        try {
            part = request.getPart("uploadfile");


            InputStream input = part.getInputStream();
            username = request.getHeader("username");
            String uniqueFilelPath =  FileUtils.generateUniqueFilePath(username);
            String targetDir = FILE_PATH_DIR + "/" + uniqueFilelPath;
            File target = new File(targetDir);
            if (!target.exists())
                target.mkdirs();
            OutputStream output = new FileOutputStream(targetDir + "/"+ part.getSubmittedFileName());
            IOUtils.copy(input, output);

            output.close();
            input.close();

            json.put("userName",username);
            json.put("fileName",part.getSubmittedFileName());
            json.put("filePath",uniqueFilelPath + "/" + part.getSubmittedFileName());
        } catch (IOException | ServletException e) {
            e.printStackTrace();
            json.put("status", -1);
        }

        json.put("status",1);

        return json;
    }


    @RequestMapping(value="/delete",produces="text/html;charset=UTF-8", method=RequestMethod.POST)
    public boolean deleteLocalfile(@RequestParam("filePath")String filePath){
        File target = new File(FILE_PATH_DIR  + "/" + filePath);
        if(target.exists()){
            return target.delete();
        }
        return false;
    }

}
