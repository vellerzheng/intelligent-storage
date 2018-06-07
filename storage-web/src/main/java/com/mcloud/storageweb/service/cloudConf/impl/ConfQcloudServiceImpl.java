package com.mcloud.storageweb.service.cloudConf.impl;


import com.mcloud.storageweb.repository.entity.ConfQcloud;
import com.mcloud.storageweb.repository.mapper.ConfQcloudMapper;
import com.mcloud.storageweb.service.cloudConf.ConfQcloudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: vellerzheng
 * @Description:
 * @Date:Created in 19:06 2018/6/3
 * @Modify By:
 */
@Service
public class ConfQcloudServiceImpl implements ConfQcloudService {
    @Autowired
    ConfQcloudMapper confQcloudMapper;
    @Override
    public int deleteByPrimaryKey(Integer id) {
        return confQcloudMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(ConfQcloud record) {
        return confQcloudMapper.insert(record);
    }

    @Override
    public int insertSelective(ConfQcloud record) {
        return confQcloudMapper.insertSelective(record);
    }

    @Override
    public ConfQcloud selectByPrimaryKey(Integer id) {
        return confQcloudMapper.selectByPrimaryKey(id);
    }

    @Override
    public ConfQcloud selectByUserIdAndStatus(Integer userId) {
        return confQcloudMapper.selectByUserIdAndStatus(userId);
    }

    @Override
    public int updateByPrimaryKeySelective(ConfQcloud record) {
        return confQcloudMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(ConfQcloud record) {
        return confQcloudMapper.updateByPrimaryKey(record);
    }
}
