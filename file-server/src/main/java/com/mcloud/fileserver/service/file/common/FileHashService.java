package com.mcloud.fileserver.service.file.common;


import com.mcloud.fileserver.repository.entity.FileHash;

/**
 * @Author: vellerzheng
 * @Description:
 * @Date:Created in 11:41 2018/6/9
 * @Modify By:
 */
public interface FileHashService {

    int deleteByPrimaryKey(Integer id);

    int insert(FileHash record);

    int insertSelective(FileHash record);

    FileHash selectByPrimaryKey(Integer id);

    FileHash selectByFileId(Integer fileId);

    int updateByPrimaryKeySelective(FileHash record);

    int updateByPrimaryKey(FileHash record);
}
