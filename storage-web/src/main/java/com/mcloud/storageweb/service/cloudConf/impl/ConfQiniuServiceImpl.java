package com.mcloud.storageweb.service.cloudConf.impl;


import com.mcloud.storageweb.repository.entity.ConfQiniu;
import com.mcloud.storageweb.repository.mapper.ConfQiniuMapper;
import com.mcloud.storageweb.service.cloudConf.ConfQiniuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: vellerzheng
 * @Description:
 * @Date:Created in 19:09 2018/6/3
 * @Modify By:
 */
@Service
public class ConfQiniuServiceImpl implements ConfQiniuService {
    @Autowired
    ConfQiniuMapper confQiniuMapper;
    @Override
    public int deleteByPrimaryKey(Integer id) {
        return confQiniuMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(ConfQiniu record) {
        return confQiniuMapper.insert(record);
    }

    @Override
    public int insertSelective(ConfQiniu record) {
        return confQiniuMapper.insertSelective(record);
    }

    @Override
    public ConfQiniu selectByPrimaryKey(Integer id) {
        return confQiniuMapper.selectByPrimaryKey(id);
    }

    @Override
    public ConfQiniu selectByUserIdAndStatus(Integer userId) {
        return confQiniuMapper.selectByUserIdAndStatus(userId);
    }

    @Override
    public int updateByPrimaryKeySelective(ConfQiniu record) {
        return confQiniuMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(ConfQiniu record) {
        return confQiniuMapper.updateByPrimaryKey(record);
    }
}
