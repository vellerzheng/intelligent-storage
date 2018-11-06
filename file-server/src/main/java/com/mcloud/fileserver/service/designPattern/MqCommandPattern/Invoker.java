package com.mcloud.fileserver.service.designPattern.MqCommandPattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: vellerzheng
 * @Description:
 * @Date:Created in 16:14 2018/6/6
 * @Modify By:
 */

@Component
public class Invoker {

    private Command command;

    public Invoker(){}

    public Invoker(Command command){
        this.command = command;
    }

    public void action(){
        command.exec();
    }
}
