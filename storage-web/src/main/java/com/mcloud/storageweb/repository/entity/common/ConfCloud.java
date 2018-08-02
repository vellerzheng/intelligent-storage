package com.mcloud.storageweb.repository.entity.common;

import com.mcloud.storageweb.repository.entity.*;

import java.io.Serializable;

/**
 * @Author: vellerzheng
 * @Description:
 * @Date:Created in 16:31 2018/8/2
 * @Modify By:
 */
public class ConfCloud implements Serializable {

    private FileHash fileHash;

    private ConfAliyun confAliyun;

    private ConfNetease confNetease;

    private ConfQcloud confQcloud;

    private ConfQiniu confQiniu;

    private ConfUpyun confUpyun;

    public void setConfAliyun(ConfAliyun confAliyun){
        this.confAliyun = confAliyun;
    }

    public ConfAliyun getConfAliyun(){
        return confAliyun;
    }

    public void setConfNetease(ConfNetease confNetease){
        this.confNetease = confNetease;
    }

    public ConfNetease getConfNetease(){
        return confNetease;
    }

    public void setConfQcloud(ConfQcloud confQcloud){
        this.confQcloud = confQcloud;
    }

    public ConfQcloud getConfQcloud(){
        return confQcloud;
    }

    public void setConfQiniu(ConfQiniu confQiniu){
        this.confQiniu = confQiniu;
    }

    public ConfQiniu getConfQiniu(){
        return confQiniu;
    }

    public void setConfUpyun(ConfUpyun confUpyun){
        this.confUpyun = confUpyun;
    }

    public ConfUpyun getConfUpyun(){
        return  confUpyun;
    }

    public void setFileHash(FileHash fileHash){
        this.fileHash = fileHash;
    }

    public FileHash getFileHash(){
        return fileHash;
    }
}
