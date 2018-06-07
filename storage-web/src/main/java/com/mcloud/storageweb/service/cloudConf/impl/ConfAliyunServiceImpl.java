package com.mcloud.storageweb.service.cloudConf.impl;


import com.mcloud.storageweb.repository.entity.ConfAliyun;
import com.mcloud.storageweb.repository.mapper.ConfAliyunMapper;
import com.mcloud.storageweb.service.cloudConf.ConfAliyunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: vellerzheng
 * @Description:
 * @Date:Created in 18:56 2018/6/3
 * @Modify By:
 */
@Service
public class ConfAliyunServiceImpl implements ConfAliyunService {
    @Autowired
    ConfAliyunMapper confAliyunMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return confAliyunMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(ConfAliyun record) {
        return confAliyunMapper.insert(record);
    }

    @Override
    public int insertSelective(ConfAliyun record) {
        return confAliyunMapper.insertSelective(record);
    }

    @Override
    public ConfAliyun selectByPrimaryKey(Integer id) {
        return confAliyunMapper.selectByPrimaryKey(id);
    }

    @Override
    public ConfAliyun selectByUserIdAndStatus(Integer userId) {
        return confAliyunMapper.selectByUserIdAndStatus(userId);
    }

    @Override
    public int updateByPrimaryKeySelective(ConfAliyun record) {
        return confAliyunMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(ConfAliyun record) {
        return confAliyunMapper.updateByPrimaryKey(record);
    }
}
