package com.mcloud.storageweb.service.cloudConf.impl;


import com.mcloud.storageweb.repository.entity.ConfUpyun;
import com.mcloud.storageweb.repository.mapper.ConfUpyunMapper;
import com.mcloud.storageweb.service.cloudConf.ConfUpyunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: vellerzheng
 * @Description:
 * @Date:Created in 19:12 2018/6/3
 * @Modify By:
 */
@Service
public class ConfUpyunServiceImpl implements ConfUpyunService {
    @Autowired
    ConfUpyunMapper confUpyunMapper;
    @Override
    public int deleteByPrimaryKey(Integer id) {
        return confUpyunMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(ConfUpyun record) {
        return confUpyunMapper.insert(record);
    }

    @Override
    public int insertSelective(ConfUpyun record) {
        return confUpyunMapper.insertSelective(record);
    }

    @Override
    public ConfUpyun selectByPrimaryKey(Integer id) {
        return confUpyunMapper.selectByPrimaryKey(id);
    }

    @Override
    public ConfUpyun selectByUserIdAndStatus(Integer userId) {
        return confUpyunMapper.selectByUserIdAndStatus(userId);
    }

    @Override
    public int updateByPrimaryKeySelective(ConfUpyun record) {
        return confUpyunMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(ConfUpyun record) {
        return confUpyunMapper.updateByPrimaryKey(record);
    }
}
