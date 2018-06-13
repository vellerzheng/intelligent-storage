package com.mcloud.storageweb.service.User;

import com.mcloud.storageweb.repository.entity.UserRole;

/**
 * @Author: vellerzheng
 * @Description:
 * @Date:Created in 18:45 2018/6/12
 * @Modify By:
 */
public interface UserRoleService {
    int deleteByPrimaryKey(Integer id);

    int insert(UserRole record);

    int insertSelective(UserRole record);

    UserRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserRole record);

    int updateByPrimaryKey(UserRole record);
}
