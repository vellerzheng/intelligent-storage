package com.mcloud.storageweb.repository.mapper;


import com.mcloud.storageweb.repository.entity.ConfAliyun;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfAliyunMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ConfAliyun record);

    int insertSelective(ConfAliyun record);

    ConfAliyun selectByPrimaryKey(Integer id);

    ConfAliyun selectByUserIdAndStatus(@Param("userId") Integer userId);

    int updateByPrimaryKeySelective(ConfAliyun record);

    int updateByPrimaryKey(ConfAliyun record);
}