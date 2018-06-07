package com.mcloud.storageweb.service.cloudConf;


import com.mcloud.storageweb.repository.entity.ConfAliyun;

/**
 * @Author: vellerzheng
 * @Description:
 * @Date:Created in 18:55 2018/6/3
 * @Modify By:
 */
public interface ConfAliyunService {
    int deleteByPrimaryKey(Integer id);

    int insert(ConfAliyun record);

    int insertSelective(ConfAliyun record);

    ConfAliyun selectByPrimaryKey(Integer id);

    ConfAliyun selectByUserIdAndStatus(Integer userId);

    int updateByPrimaryKeySelective(ConfAliyun record);

    int updateByPrimaryKey(ConfAliyun record);
}
