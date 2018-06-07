package com.mcloud.dbprovider.mongdbTest;


import com.mcloud.dbprovider.repository.mongdb.entity.MongoUser;
import com.mcloud.dbprovider.repository.mongdb.mapper.MsgInfoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MongoUserRepositoryTest {
    private Logger logger =  LoggerFactory.getLogger(this.getClass());
    @Autowired
    MsgInfoRepository msgInfoRepository;

    @Test
    public void add(){
        MongoUser mongoUser=new MongoUser();
        String id= UUID.randomUUID().toString();
        mongoUser.setId(id);
        mongoUser.setUserId(112222);
        mongoUser.setUserName("江海通");
        msgInfoRepository.save(mongoUser);



        Example<String> example1 = Example.of(id);
        Optional<MongoUser> mongoUser1 = msgInfoRepository.findById(id);
        System.out.println(mongoUser1.orElse(null));

        List<MongoUser> list = msgInfoRepository.findAll();
        for(MongoUser mgUser : list)
            System.out.println("All user is :" + mgUser);
        msgInfoRepository.deleteAll();
        List<MongoUser> list1 = msgInfoRepository.findAll();

    }


}
