package com.hvisions.mes.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.domain.Tooling;
import com.hvisions.mes.domain.ToolingRepair;
import com.hvisions.mes.mapper.ToolingRepairMapper;
import com.hvisions.mes.service.IToolingRepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dpeng
 * @description
 * @date 2019-08-27 19:56
 */
@Service
public class ToolingRepairServiceImpl implements IToolingRepairService {

    @Autowired
    private ToolingRepairMapper repairMapper;

    /**
     *  分页查询工装信息
     * @param page    分页对象
     * @param repair  工装维保对象
     * @return        工装维保
     */
    @Override
    public Page<ToolingRepair> queryAllByPage(Page<ToolingRepair> page, ToolingRepair repair) {
        page.setRecords(repairMapper.selectAllByPage(page,repair));
        return page;
    }

    /**
     *  添加工装维保信息
     * @param repair 工装维保对象
     */
    @Override
    public void appendToolingRepair(ToolingRepair repair) {
        repairMapper.insertToolingRepair(repair);
    }

    /**
     *  修改工装维保信息
     * @param repair
     */
    @Override
    public void changeToolingRepair(ToolingRepair repair) {
        repairMapper.updateToolingRepair(repair);
    }

    /**
     * 通过ID删除工装维保信息
     * @param ids 工装维保ID
     */
    @Override
    public void cutToolingRepairById(List<Integer> ids) {
        repairMapper.deleteToolingRepairById(ids);
    }

    /**
     *  查询所有的工装名称和ID
     * @return  工装名称和ID
     */
    @Override
    public List<Tooling> queryToolingName() {
        return repairMapper.selectToolingName();
    }
}
