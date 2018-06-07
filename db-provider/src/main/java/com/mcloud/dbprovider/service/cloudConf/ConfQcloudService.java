package com.mcloud.dbprovider.service.cloudConf;

import com.mcloud.dbprovider.repository.entity.ConfQcloud;

/**
 * @Author: vellerzheng
 * @Description:
 * @Date:Created in 19:02 2018/6/3
 * @Modify By:
 */
public interface ConfQcloudService {
    int deleteByPrimaryKey(Integer id);

    int insert(ConfQcloud record);

    int insertSelective(ConfQcloud record);

    ConfQcloud selectByPrimaryKey(Integer id);

    ConfQcloud selectByUserIdAndStatus(Integer userId);

    int updateByPrimaryKeySelective(ConfQcloud record);

    int updateByPrimaryKey(ConfQcloud record);
}
