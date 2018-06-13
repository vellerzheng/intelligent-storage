package com.mcloud.storageweb.service.User;

import com.mcloud.storageweb.repository.entity.Role;

import java.util.List;

/**
 * @Author: vellerzheng
 * @Description:
 * @Date:Created in 18:00 2018/6/12
 * @Modify By:
 */
public interface RoleService {
    int deleteByPrimaryKey(Integer roleid);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer roleid);

    List<Role> roleBelongsUsername(String username);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
}
