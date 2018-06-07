package com.mcloud.fileserver.service.User;

import com.mcloud.fileserver.repository.entity.User;
import com.mcloud.fileserver.service.User.impl.UserServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: vellerzheng
 * @Description:
 * @Date:Created in 20:38 2018/6/1
 * @Modify By:
 */
@FeignClient(value="db-provider",fallback = UserServiceImpl.class)
public interface UserService {

    @RequestMapping(value = "/user")
    User getUser(@RequestParam("id")Integer id);
}
