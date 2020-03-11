package com.hvisions.mes.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.domain.Tooling;
import com.hvisions.mes.domain.ToolingRepair;

import java.util.List;

/**
 * @author dpeng
 * @description 工装维保
 * @date 2019-08-27 19:53
 */
public interface IToolingRepairService {

    /**
     *  分页查询工装信息
     * @param page    分页对象
     * @param repair  工装维保对象
     * @return        工装维保
     */
    Page<ToolingRepair> queryAllByPage(Page<ToolingRepair> page, ToolingRepair repair);

    /**
     *  添加工装维保信息
     * @param repair 工装维保对象
     */
    void appendToolingRepair(ToolingRepair repair);

    /**
     *  修改工装维保信息
     * @param repair
     */
    void changeToolingRepair(ToolingRepair repair);

    /**
     * 通过ID删除工装维保信息
     * @param ids 工装维保ID
     */
    void cutToolingRepairById(List<Integer> ids);

    /**
     *  查询所有的工装名称和ID
     * @return  工装名称和ID
     */
    List<Tooling> queryToolingName();
}
