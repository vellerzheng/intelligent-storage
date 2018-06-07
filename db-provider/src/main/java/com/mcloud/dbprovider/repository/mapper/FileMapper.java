package com.mcloud.dbprovider.repository.mapper;

import com.mcloud.dbprovider.repository.entity.File;
import org.springframework.stereotype.Repository;

@Repository
public interface FileMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(File record);

    int insertSelective(File record);

    File selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(File record);

    int updateByPrimaryKey(File record);
}