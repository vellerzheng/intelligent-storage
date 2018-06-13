package com.mcloud.storageweb.service.User.impl;



import com.mcloud.storageweb.repository.entity.User;
import com.mcloud.storageweb.repository.entity.UserRole;
import com.mcloud.storageweb.repository.mapper.UserMapper;
import com.mcloud.storageweb.repository.mapper.UserRoleMapper;
import com.mcloud.storageweb.service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Autowired
    UserRoleMapper userRoleMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(User record) {
        return userMapper.insert(record);
    }

    @Override
    public int insertSelective(User record) {
        return userMapper.insertSelective(record);
    }

    @Override
    public User selectByPrimaryKey(Integer id) {

        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public User selectByUserName(String username) {

        return userMapper.selectByUserName(username);
    }

    @Override
    public int updateByPrimaryKeySelective(User record) {

        return userMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(User record) {
        return userMapper.updateByPrimaryKey(record);
    }

    @Override
    @Async
    public boolean setUserRole(String username, Integer roleId) {
        User user = userMapper.selectByUserName(username);
        UserRole userRole = new UserRole();
        userRole.setUserId(user.getId());
        userRole.setRoleId(roleId);
        userRoleMapper.insertSelective(userRole);
        return true;
    }
}
