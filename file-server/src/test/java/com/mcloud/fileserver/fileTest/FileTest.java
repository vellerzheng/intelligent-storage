package com.mcloud.fileserver.fileTest;

/**
 * @Author: vellerzheng
 * @Description:
 * @Date:Created in 21:17 2018/6/6
 * @Modify By:
 **/

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.mcloud.fileserver.util.FileUtil;
import org.junit.Test;

public class FileTest {

    @Test
    public void writeFile() throws IOException, InterruptedException {

        System.out.println(FileUtil.currentWorkDir);

        StringBuilder sb = new StringBuilder();

        long originFileSize = 1024 * 1024 * 10;// 10M
//        int blockFileSize = 1024 * 1024 * 2;// 2M

/*        // 生成一个大文件
        for (int i = 0; i < originFileSize; i++) {
            sb.append("A");
        }

        String fileName = FileUtil.currentWorkDir + "origin.myfile";
        System.out.println(fileName);
        System.out.println(FileUtil.write(fileName, sb.toString()));

        // 追加内容
        sb.setLength(0);
        sb.append("0123456789");
        FileUtil.append(fileName, sb.toString());*/

        String fileName = "D:\\Test\\split\\分布式网络架构.pdf";
        FileUtil fileUtil = new FileUtil();

        File file = new File(fileName);
        long fileSize = file.length();
        int blockFileSize = (int)fileSize/4;

                // 将origin.myfile拆分
        List<String> list= fileUtil.splitBySize(fileName, blockFileSize);
        for(String ls: list)
            System.out.println("文件目录： " + ls);

        Thread.sleep(8000);// 稍等10秒，等前面的小文件全都写完

        String sourcetPath ="D:\\Test\\split\\split";
        String destPath ="D:\\Test\\split\\merge";
        // 合并成新文件
        fileUtil.mergePartFiles(sourcetPath, ".part",
                blockFileSize, destPath + "\\分布式网络架构.pdf");

    }
}