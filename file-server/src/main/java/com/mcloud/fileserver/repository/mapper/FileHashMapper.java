package com.mcloud.fileserver.repository.mapper;


import com.mcloud.fileserver.repository.entity.FileHash;
import org.springframework.stereotype.Repository;

@Repository
public interface FileHashMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FileHash record);

    int insertSelective(FileHash record);

    FileHash selectByPrimaryKey(Integer id);

    FileHash selectByFileId(Integer fileId);

    int updateByPrimaryKeySelective(FileHash record);

    int updateByPrimaryKey(FileHash record);

}