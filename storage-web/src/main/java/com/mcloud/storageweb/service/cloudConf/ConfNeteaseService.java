package com.mcloud.storageweb.service.cloudConf;


import com.mcloud.storageweb.repository.entity.ConfNetease;

/**
 * @Author: vellerzheng
 * @Description:
 * @Date:Created in 19:01 2018/6/3
 * @Modify By:
 */
public interface ConfNeteaseService {
    int deleteByPrimaryKey(Integer id);

    int insert(ConfNetease record);

    int insertSelective(ConfNetease record);

    ConfNetease selectByPrimaryKey(Integer id);

    ConfNetease selectByUserIdAndStatus(Integer userId);

    int updateByPrimaryKeySelective(ConfNetease record);

    int updateByPrimaryKey(ConfNetease record);
}
