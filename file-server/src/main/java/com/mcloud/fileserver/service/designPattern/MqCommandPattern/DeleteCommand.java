package com.mcloud.fileserver.service.designPattern.MqCommandPattern;

import com.mcloud.fileserver.service.file.fileOperate.delete.DeleteService;
import org.springframework.stereotype.Component;

/**
 * @Author: vellerzheng
 * @Description:
 * @Date:Created in 16:17 2018/6/6
 * @Modify By:
 */

@Component
public class DeleteCommand implements Command {


    private DeleteService deleteService;

    public DeleteCommand(){}

    public DeleteCommand(DeleteService deleteService){
        this.deleteService = deleteService;
    }

    @Override
    public void exec() {
        deleteService.deleteFile();
    }
}
