package com.mcloud.fileserver.service.file.common;



import com.mcloud.fileserver.repository.entity.FileEntity;
import com.mcloud.fileserver.repository.mapper.FileEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<FileEntity> selectListByUserId(Integer userId) {
        return fileEntityMapper.selectListByUserId(userId);
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
