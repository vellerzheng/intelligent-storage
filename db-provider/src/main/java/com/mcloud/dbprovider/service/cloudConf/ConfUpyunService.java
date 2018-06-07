package com.mcloud.dbprovider.service.cloudConf;

import com.mcloud.dbprovider.repository.entity.ConfUpyun;

/**
 * @Author: vellerzheng
 * @Description:
 * @Date:Created in 19:02 2018/6/3
 * @Modify By:
 */
public interface ConfUpyunService {
    int deleteByPrimaryKey(Integer id);

    int insert(ConfUpyun record);

    int insertSelective(ConfUpyun record);

    ConfUpyun selectByPrimaryKey(Integer id);

    ConfUpyun selectByUserIdAndStatus(Integer userId);

    int updateByPrimaryKeySelective(ConfUpyun record);

    int updateByPrimaryKey(ConfUpyun record);
}
