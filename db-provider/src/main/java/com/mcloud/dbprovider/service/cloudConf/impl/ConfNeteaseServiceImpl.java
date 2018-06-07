package com.mcloud.dbprovider.service.cloudConf.impl;

import com.mcloud.dbprovider.repository.entity.ConfNetease;
import com.mcloud.dbprovider.repository.mapper.ConfNeteaseMapper;
import com.mcloud.dbprovider.service.cloudConf.ConfNeteaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: vellerzheng
 * @Description:
 * @Date:Created in 19:03 2018/6/3
 * @Modify By:
 */
@Service
public class ConfNeteaseServiceImpl implements ConfNeteaseService {
    @Autowired
    ConfNeteaseMapper confNeteaseMapper;
    @Override
    public int deleteByPrimaryKey(Integer id) {
        return confNeteaseMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(ConfNetease record) {
        return confNeteaseMapper.insert(record);
    }

    @Override
    public int insertSelective(ConfNetease record) {
        return confNeteaseMapper.insertSelective(record);
    }

    @Override
    public ConfNetease selectByPrimaryKey(Integer id) {
        return confNeteaseMapper.selectByPrimaryKey(id);
    }

    @Override
    public ConfNetease selectByUserIdAndStatus(Integer userId) {
        return confNeteaseMapper.selectByUserIdAndStatus(userId);
    }

    @Override
    public int updateByPrimaryKeySelective(ConfNetease record) {
        return confNeteaseMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(ConfNetease record) {
        return confNeteaseMapper.updateByPrimaryKey(record);
    }
}
