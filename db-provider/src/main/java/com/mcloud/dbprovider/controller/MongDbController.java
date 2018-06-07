package com.mcloud.dbprovider.controller;

import com.mcloud.dbprovider.repository.mongdb.entity.MongoUser;
import com.mcloud.dbprovider.repository.mongdb.mapper.MsgInfoRepository;
import com.mcloud.dbprovider.util.InfoJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @Author: vellerzheng
 * @Description:
 * @Date:Created in 15:46 2018/6/1
 * @Modify By:
 */
@RestController
public class MongDbController {

    @Autowired
    MsgInfoRepository msgInfoRepository;
    private Logger logger = LoggerFactory.getLogger(MongDbController.class);

    @RequestMapping(value = "/mongdb")
    public List<MongoUser>  MongDbTest() {
        MongoUser mongoUser = new MongoUser();
        String id = UUID.randomUUID().toString();
        mongoUser.setId(id);
        mongoUser.setUserId(112222);
        mongoUser.setUserName("江海通");
        msgInfoRepository.save(mongoUser);


        Example<String> example1 = Example.of(id);
        Optional<MongoUser> mongoUser1 = msgInfoRepository.findById(id);
        System.out.println(mongoUser1.orElse(null));

        List<MongoUser> list = msgInfoRepository.findAll();
        for (MongoUser mgUser : list)
            System.out.println("All user is :" + mgUser);
        msgInfoRepository.deleteAll();
        List<MongoUser> list1 = msgInfoRepository.findAll();
        logger.error("test Successfull!");
        return list;
    }
}
