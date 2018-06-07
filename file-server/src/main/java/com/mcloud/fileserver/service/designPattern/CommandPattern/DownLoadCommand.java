package com.mcloud.fileserver.service.designPattern.CommandPattern;

import com.alibaba.fastjson.JSONObject;
import com.mcloud.fileserver.service.file.fileOperate.download.DownLoadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: vellerzheng
 * @Description:
 * @Date:Created in 16:12 2018/6/6
 * @Modify By:
 */

@Component
public class DownLoadCommand implements Command {


    private DownLoadService downLoadService;


    public DownLoadCommand(DownLoadService downLoadService){
        this.downLoadService = downLoadService;
    }


    @Override
    public void exec() {
        downLoadService.DownLoadFile();
    }
}
