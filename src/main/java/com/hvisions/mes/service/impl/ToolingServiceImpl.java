package com.hvisions.mes.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.domain.Tooling;
import com.hvisions.mes.mapper.ToolingMapper;
import com.hvisions.mes.service.IToolingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author dpeng
 * @description
 * @date 2019-08-27 16:22
 */
@Service
public class ToolingServiceImpl implements IToolingService {

    @Autowired
    private ToolingMapper toolingMapper;

    /**
     *  分页查询工装信息
     * @param page    分页对象
     * @param tooling 工装对象
     * @return        工装
     */
    @Override
    public Page<Tooling> selectAllByPage(Page<Tooling> page, Tooling tooling) {
        page.setRecords(toolingMapper.selectAllByPage(page,tooling));
        return page;
    }

    /**
     *  添加工装信息
     * @param tooling  工装对象
     */
    @Override
    public void insertTooling(Tooling tooling) {
        toolingMapper.insertTooling(tooling);
    }

    /**
     *  修改工装信息
     * @param tooling  工装信息
     */
    @Override
    public void updateTooling(Tooling tooling) {
        toolingMapper.updateTooling(tooling);
    }

    /**
     * 通过ID删除工装信息
     * @param ids 工装ID
     */
    @Override
    public void deleteToolingById(List<Integer> ids) {
        toolingMapper.deleteToolingById(ids);
    }
}
