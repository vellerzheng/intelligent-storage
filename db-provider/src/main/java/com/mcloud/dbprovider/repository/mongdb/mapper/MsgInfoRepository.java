package com.mcloud.dbprovider.repository.mongdb.mapper;


import com.mcloud.dbprovider.repository.mongdb.entity.MongoUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MsgInfoRepository extends MongoRepository<MongoUser,String> {

    MongoUser queryMsgInfoByTitle(String title);
}
