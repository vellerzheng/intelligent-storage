package com.mcloud.fileserver.repository.entity;

import java.io.Serializable;
import java.util.Date;

public class FileHash implements Serializable {
    private Integer id;

    private Integer fileId;

    private String fileHash;

    private String aliyun;

    private String netease;

    private String qcloud;

    private String qiniu;

    private String upyun;

    private String creator;

    private Date createtime;

    private Date updatetime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getFileHash() {
        return fileHash;
    }

    public void setFileHash(String fileHash) {
        this.fileHash = fileHash == null ? null : fileHash.trim();
    }

    public String getAliyun() {
        return aliyun;
    }

    public void setAliyun(String aliyun) {
        this.aliyun = aliyun == null ? null : aliyun.trim();
    }

    public String getNetease() {
        return netease;
    }

    public void setNetease(String netease) {
        this.netease = netease == null ? null : netease.trim();
    }

    public String getQcloud() {
        return qcloud;
    }

    public void setQcloud(String qcloud) {
        this.qcloud = qcloud == null ? null : qcloud.trim();
    }

    public String getQiniu() {
        return qiniu;
    }

    public void setQiniu(String qiniu) {
        this.qiniu = qiniu == null ? null : qiniu.trim();
    }

    public String getUpyun() {
        return upyun;
    }

    public void setUpyun(String upyun) {
        this.upyun = upyun == null ? null : upyun.trim();
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }
}