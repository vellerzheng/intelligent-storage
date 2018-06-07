package com.mcloud.dbprovider.repository.mapper;

import com.mcloud.dbprovider.repository.entity.ConfUpyun;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfUpyunMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ConfUpyun record);

    int insertSelective(ConfUpyun record);

    ConfUpyun selectByPrimaryKey(Integer id);

    ConfUpyun selectByUserIdAndStatus(@Param("userId")Integer userId);

    int updateByPrimaryKeySelective(ConfUpyun record);

    int updateByPrimaryKey(ConfUpyun record);
}