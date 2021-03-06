package com.mcloud.fileserver.service.designPattern.MqCommandPattern;

import com.mcloud.fileserver.service.file.fileOperate.download.DownLoadService;
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

    public DownLoadCommand(){}

    public DownLoadCommand(DownLoadService downLoadService){
        this.downLoadService = downLoadService;
    }


    @Override
    public void exec() {
        downLoadService.downLoadFile();
    }
}
