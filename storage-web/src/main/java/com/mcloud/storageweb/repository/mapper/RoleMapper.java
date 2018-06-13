package com.mcloud.storageweb.repository.mapper;

import com.mcloud.storageweb.repository.entity.Role;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleMapper {
    int deleteByPrimaryKey(Integer roleid);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer roleid);

    List<Role> roleBelongsUsername(String username);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
}