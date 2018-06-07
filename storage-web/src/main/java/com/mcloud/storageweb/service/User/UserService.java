package com.mcloud.storageweb.service.User;


import com.mcloud.storageweb.repository.entity.User;

public interface UserService {
    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}
