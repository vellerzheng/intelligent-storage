package com.mcloud.storageweb.service.User;


import com.mcloud.storageweb.repository.entity.User;

public interface UserService {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    User selectByUserName(String username);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    boolean setUserRole(String username, Integer roleId);
}
