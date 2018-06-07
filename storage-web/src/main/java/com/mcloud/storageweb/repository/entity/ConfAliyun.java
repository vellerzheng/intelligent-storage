package com.mcloud.storageweb.repository.entity;

import java.io.Serializable;
import java.util.Date;

public class ConfAliyun  implements Serializable {
    private Integer id;

    private String endpoint;

    private String accesskey;

    private String accesskeysecret;

    private String bucketname;

    private String accessurl;

    private Integer status;

    private Integer version;

    private Integer userId;

    private String creator;

    private Date createtime;

    private Date updatetime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint == null ? null : endpoint.trim();
    }

    public String getAccesskey() {
        return accesskey;
    }

    public void setAccesskey(String accesskey) {
        this.accesskey = accesskey == null ? null : accesskey.trim();
    }

    public String getAccesskeysecret() {
        return accesskeysecret;
    }

    public void setAccesskeysecret(String accesskeysecret) {
        this.accesskeysecret = accesskeysecret == null ? null : accesskeysecret.trim();
    }

    public String getBucketname() {
        return bucketname;
    }

    public void setBucketname(String bucketname) {
        this.bucketname = bucketname == null ? null : bucketname.trim();
    }

    public String getAccessurl() {
        return accessurl;
    }

    public void setAccessurl(String accessurl) {
        this.accessurl = accessurl == null ? null : accessurl.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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