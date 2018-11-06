package com.mcloud.fileserver.repository.mapper;


import com.mcloud.fileserver.repository.entity.FileEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FileEntity record);

    int insertSelective(FileEntity record);

    FileEntity selectByPrimaryKey(Integer id);

    List<FileEntity> selectListByUserId(Integer userId);

    int updateByPrimaryKeySelective(FileEntity record);

    int updateByPrimaryKey(FileEntity record);
}