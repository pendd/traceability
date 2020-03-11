package com.hvisions.mes.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.hvisions.mes.domain.SysConfig;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author fushuai
 * @since 2017-05-25
 */
public interface ISysConfigService extends IService<SysConfig> {

    public List<SysConfig> selectSysConfig();

    public void addSysConfig(SysConfig sysConfig);

    public void removeSysConfig();

    //    public Map<Integer , String> select
}
