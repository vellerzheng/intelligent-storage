package com.mcloud.storageweb.common.constant;

/**
 * @Author: vellerzheng
 * @Description:
 * @Date:Created in 13:29 2018/6/15
 * @Modify By:
 */
public class FileConst {

   public  final static Integer UPLOAD_SUCCESS = 1; // 本地上传成功，等待处理

   public  final static Integer UPLOAD_FAIL = -1;

   public  final static Integer UPLOAD_HANDLING = 2;         //本地正在上传

   public final  static Integer CLOUD_UPLOADING = 3;  //云上传中

   public final static Integer CLOUD_UPLOADED = 4;  // 云上传完成
}
