package com.mcloud.storageweb.repository.entity.common;

/**
 * @Author: vellerzheng
 * @Description:
 * @Date:Created in 11:39 2018/11/16
 * @Modify By:
 */
public class BaseResponse {

    public BaseResponse(Object content) {
        this.content = content;
    }

    private Object content;

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }
}
