package com.mcloud.storageweb.repository.mapper;


import com.mcloud.storageweb.repository.entity.ConfQiniu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfQiniuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ConfQiniu record);

    int insertSelective(ConfQiniu record);

    ConfQiniu selectByPrimaryKey(Integer id);

    ConfQiniu selectByUserIdAndStatus(@Param("userId") Integer userId);

    int updateByPrimaryKeySelective(ConfQiniu record);

    int updateByPrimaryKey(ConfQiniu record);
}