package com.mcloud.fileserver.service.designPattern.MqCommandPattern;

/**
 * @Author: vellerzheng
 * @Description:
 * @Date:Created in 16:14 2018/6/6
 * @Modify By:
 */

public class Invoker {
    private Command command;

    public Invoker(Command command){
        this.command = command;
    }

    public void action(){
        command.exec();
    }
}
