package com.hvisions.mes.mapper;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hvisions.mes.domain.SysConfig;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author fushuai
 * @since 2017-05-25
 */
public interface SysConfigMapper extends BaseMapper<SysConfig> {
    public List<SysConfig> querySysConfig();

    public void insertSysConfig(SysConfig sysConfig);

    public void deleteSysConfig();
}