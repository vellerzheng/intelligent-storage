package com.mcloud.storageweb.repository.mapper;


import com.mcloud.storageweb.repository.entity.ConfQcloud;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfQcloudMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ConfQcloud record);

    int insertSelective(ConfQcloud record);

    ConfQcloud selectByPrimaryKey(Integer id);

    ConfQcloud selectByUserIdAndStatus(@Param("userId") Integer userId);

    int updateByPrimaryKeySelective(ConfQcloud record);

    int updateByPrimaryKey(ConfQcloud record);
}