package com.mcloud.dbprovider.repository.mapper;

import com.mcloud.dbprovider.repository.entity.ConfNetease;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfNeteaseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ConfNetease record);

    int insertSelective(ConfNetease record);

    ConfNetease selectByPrimaryKey(Integer id);

    ConfNetease selectByUserIdAndStatus(@Param("userId")Integer userId);

    int updateByPrimaryKeySelective(ConfNetease record);

    int updateByPrimaryKey(ConfNetease record);
}