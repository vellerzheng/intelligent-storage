package com.mcloud.fileserver.service.file.common;



import com.mcloud.fileserver.repository.entity.FileEntity;
import com.mcloud.fileserver.repository.entity.FileHash;

import com.mcloud.fileserver.repository.mapper.FileHashMapper;
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
    @Autowired
    FileService fileService;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return fileHashMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(FileHash record) {

        int res = fileHashMapper.insert(record);

        FileEntity fileEntity = fileService.selectByPrimaryKey(record.getFileId());
        fileEntity.setStatus(4); //云服务处理完成
        fileService.updateByPrimaryKeySelective(fileEntity);
        return res;
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
