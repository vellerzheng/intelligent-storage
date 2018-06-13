package com.mcloud.storageweb.service.User.impl;

import com.mcloud.storageweb.repository.entity.Role;
import com.mcloud.storageweb.repository.entity.User;
import com.mcloud.storageweb.service.User.RoleService;
import com.mcloud.storageweb.service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: vellerzheng
 * @Description:
 * @Date:Created in 17:20 2018/6/12
 * @Modify By:
 */
@Service
public class AuthUserRoleService implements UserDetailsService {

    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;


    @Override
    public UserDetails loadUserByUsername(String username){
        User user = userService.selectByUserName(username);
        if(user == null){
            throw new UsernameNotFoundException("用户名不存在");
        }
        List<Role> roles = roleService.roleBelongsUsername(username);

        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        //写入用户的角色  ***  切记 由于框架原因 角色名称要以 ROLE_ 开头 ****
        for(Role role: roles){
            if(role != null && role.getRolename()!=null){
                SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getRolename());
                authorities.add(grantedAuthority);
            }
        }
        return  new org.springframework.security.core.userdetails.User(username, user.getPassword(), authorities);
    }
}
