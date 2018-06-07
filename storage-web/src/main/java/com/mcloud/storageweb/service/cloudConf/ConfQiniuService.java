package com.mcloud.storageweb.service.cloudConf;


import com.mcloud.storageweb.repository.entity.ConfQiniu;

/**
 * @Author: vellerzheng
 * @Description:
 * @Date:Created in 19:02 2018/6/3
 * @Modify By:
 */
public interface ConfQiniuService {
    int deleteByPrimaryKey(Integer id);

    int insert(ConfQiniu record);

    int insertSelective(ConfQiniu record);

    ConfQiniu selectByPrimaryKey(Integer id);

    ConfQiniu selectByUserIdAndStatus(Integer userId);

    int updateByPrimaryKeySelective(ConfQiniu record);

    int updateByPrimaryKey(ConfQiniu record);
}
