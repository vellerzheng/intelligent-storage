package com.mcloud.storageweb.service.file;

import com.mcloud.storageweb.repository.entity.FileEntity;
import com.mcloud.storageweb.repository.entity.User;

import java.util.List;

/**
 * @Author: vellerzheng
 * @Description:
 * @Date:Created in 19:36 2018/6/5
 * @Modify By:
 */
public interface FileService {

    int deleteByPrimaryKey(Integer id);

    int insert(FileEntity record);

    int insertSelective(FileEntity record);

    FileEntity selectByPrimaryKey(Integer id);

    List<FileEntity> selectListByUserId(Integer userId);

    int updateByPrimaryKeySelective(FileEntity record);

    int updateByPrimaryKey(FileEntity record);


}
