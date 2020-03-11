package com.hvisions.mes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hvisions.mes.domain.SysConfig;
import com.hvisions.mes.mapper.SysConfigMapper;
import com.hvisions.mes.service.ISysConfigService;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author fushuai
 * @since 2017-05-25
 */
@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig>
        implements ISysConfigService {
    @Autowired
    private SysConfigMapper sysConfigMapper;

    @Override
    public List<SysConfig> selectSysConfig() {
        return sysConfigMapper.querySysConfig();
    }

    @Override
    public void addSysConfig(SysConfig sysConfig) {
        sysConfigMapper.insertSysConfig(sysConfig);
    }

    @Override
    public void removeSysConfig() {
        sysConfigMapper.deleteSysConfig();
    }

}
