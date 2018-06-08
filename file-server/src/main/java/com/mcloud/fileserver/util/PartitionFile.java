package com.mcloud.fileserver.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vellerzheng on 2017/8/14.
 */
public class PartitionFile {
    /**
     * cut file
     * @param srcFile   source file
     * @param fileSize  the size after devision , MB
     *
     */
    public static List<String> split(File srcFile,int fileSize){
        if("".equals(srcFile)|| srcFile==null ||fileSize==0 ){
            System.out.println("分割失败！");
        }
        List<String> res = new ArrayList<>();
        long srcSize=srcFile.length();  //source file size
        long destSize=1024*1024*fileSize;  // target file size

        int number=(int)(srcSize/destSize);
        number = srcSize%destSize==0? number:number+1; // the file number finished devision

        String dest = srcFile.getParent()+ "/" +"split";
        File destFile = new File(dest);
        if(!destFile.exists())
            destFile.mkdirs();

        String fileName = FileManage.getMD5ByFile(srcFile);  // the name of source file
        InputStream in = null;
        BufferedInputStream bis =null; // input cache stream
        byte[] bytes = new byte[1024*1024];
        int len=-1; // the length of each reading
        try {
            in = new FileInputStream(srcFile);
            bis = new BufferedInputStream(in);
            for(int i=0;i<number;i++){
                String destName = dest+File.separator+fileName+"-"+i+".dat";
                res.add(destName);
                OutputStream out = new FileOutputStream(destName);
                BufferedOutputStream bos = new BufferedOutputStream(out);
                int count =0;
                while((len=bis.read(bytes))!=-1){
                    bos.write(bytes,0,len);
                    count+=len;
                    if(count>=destSize){
                        break;
                    }
                }
                bos.flush();
                bos.close();
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            // close stream
            try{
                if(bis!=null) bis.close();
                if(in!= null) in.close();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        return res;
    }

    /**
     *  combine file
     *  @param destPath  target file path
     *  @param srcPaths   source file Path
     */
    public static String merge(List<String> srcPaths,String destPath){
        if(destPath==null|| "".equals(destPath)||srcPaths==null){
            System.out.println("merge file fail!");
        }
        if(srcPaths!=null)
        for(String string :srcPaths){
            if("".equals(string)||string==null){
                System.out.println("merge file fail!");
            }
        }

        //the file name after merged
    //    String name = srcPaths.get(0).substring((srcPaths.get(0).lastIndexOf(File.separator)));
    //    String destName = name.substring(0,name.lastIndexOf("-"));
    //    String realDestPath = destPath+File.separator+destName.replace(File.separator,""); //  the file path after merged

        File destFile = new File(destPath); // the file after merged
        OutputStream out =null;
        BufferedOutputStream bos = null;
        try {
            out = new FileOutputStream(destFile);
            bos = new BufferedOutputStream(out);
            for(String src:srcPaths) {
                File srcFile = new File(src);
                InputStream in = new FileInputStream(srcFile);
                BufferedInputStream bis = new BufferedInputStream(in);
                byte[] bytes = new byte[1024*1024];
                int len =-1;
                while((len=bis.read(bytes))!=-1){
                    bos.write(bytes,0,len);
                }
                bis.close();
                in.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                if(bos!=null) bos.close();
                if(out!=null) out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return destPath;
    }

    public static void main(String[] args){
        /*
         * test cut file
         */


        long startTime1=System.currentTimeMillis();   //获取开始时间
        String src = "D:\\Test\\split\\分布式网络架构.pdf";
        File file= new File(src);
        String fileName = file.getName();
        int fileSize = (int)file.length()/1024/1024/3;     //  unit  MB  , each file after splited
        System.out.println("Split file start...");
        PartitionFile partitionFile= new PartitionFile();
        List<String> result = partitionFile.split(file,fileSize);
        for(String str :result){
            System.out.println("文件名路径： "+ str);
        }
        System.out.println("Split file finished!");
        long endTime1=System.currentTimeMillis(); //获取结束时间
        System.out.println("程序split()运行时间： "+(endTime1-startTime1)+"ms");



        /**
         * merge file test
         */

        long startTime=System.currentTimeMillis();   //获取开始时间
        String destPath ="D:\\Test\\split\\merge" +"/" + fileName;
        // the file path should be merged

        System.out.println("Start merging file...");
        partitionFile.merge(result,destPath);
        System.out.println("merge file finished!");
        long endTime=System.currentTimeMillis(); //获取结束时间
        System.out.println("程序运行时间： "+(endTime-startTime)+"ms");

    }
}
