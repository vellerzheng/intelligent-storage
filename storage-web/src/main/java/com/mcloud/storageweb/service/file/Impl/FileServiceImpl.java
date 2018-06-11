package com.mcloud.storageweb.service.file.Impl;


import com.mcloud.storageweb.repository.entity.*;
import com.mcloud.storageweb.repository.mapper.FileEntityMapper;
import com.mcloud.storageweb.service.file.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: vellerzheng
 * @Description:
 * @Date:Created in 19:45 2018/6/5
 * @Modify By:
 */
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    FileEntityMapper fileEntityMapper;



    @Override
    public int deleteByPrimaryKey(Integer id) {
        return fileEntityMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(FileEntity record) {
        return fileEntityMapper.insert(record);
    }

    @Override
    public int insertSelective(FileEntity record) {
        return fileEntityMapper.insertSelective(record);
    }

    @Override
    public FileEntity selectByPrimaryKey(Integer id) {
        return fileEntityMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(FileEntity record) {
        return fileEntityMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(FileEntity record) {
        return fileEntityMapper.updateByPrimaryKey(record);
    }


}
