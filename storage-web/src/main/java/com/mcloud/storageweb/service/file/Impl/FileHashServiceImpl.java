package com.mcloud.storageweb.service.file.Impl;

import com.mcloud.storageweb.repository.entity.FileHash;
import com.mcloud.storageweb.repository.mapper.FileHashMapper;
import com.mcloud.storageweb.service.file.FileHashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: vellerzheng
 * @Description:
 * @Date:Created in 11:41 2018/6/9
 * @Modify By:
 */
@Service
public class FileHashServiceImpl implements FileHashService {

    @Autowired
    FileHashMapper fileHashMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return fileHashMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(FileHash record) {
        return fileHashMapper.insert(record);
    }

    @Override
    public int insertSelective(FileHash record) {
        return fileHashMapper.insertSelective(record);
    }

    @Override
    public FileHash selectByPrimaryKey(Integer id) {
        return fileHashMapper.selectByPrimaryKey(id);
    }

    @Override
    public FileHash selectByFileId(Integer fileId) {
        return fileHashMapper.selectByFileId(fileId);
    }

    @Override
    public int updateByPrimaryKeySelective(FileHash record) {
        return fileHashMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(FileHash record) {
        return fileHashMapper.updateByPrimaryKey(record);
    }
}
