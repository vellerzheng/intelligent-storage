package com.mcloud.storageweb.repository.mapper;

import com.mcloud.storageweb.repository.entity.FileEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface FileEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FileEntity record);

    int insertSelective(FileEntity record);

    FileEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FileEntity record);

    int updateByPrimaryKey(FileEntity record);
}