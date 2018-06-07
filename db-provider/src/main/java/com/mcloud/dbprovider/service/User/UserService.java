package com.mcloud.dbprovider.service.User;


import com.mcloud.dbprovider.repository.entity.User;

public interface UserService {
    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}
