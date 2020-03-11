package com.hvisions.mes.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.domain.EmpLineOrderTeam;
import com.hvisions.mes.domain.Goods;

import java.util.List;

/**
 * 员工产线工序班组
 *
 * @author dpeng
 * @create 2019-06-27 10:16
 */
public interface IEmpLineOrderTeamService {
    /**
     *  查询员工产线工序班组配置信息
     * @param empLineOrderTeam 配置对象    可能出现值 empName lineName orderName teamName
     * @return
     */
    Page<EmpLineOrderTeam> queryAllEmpLineOrderTeam(Page<EmpLineOrderTeam> page,EmpLineOrderTeam empLineOrderTeam);

    /**
     *  新增记录
     * @param empLineOrderTeam
     */
    void addEmpLineOrderTeam(EmpLineOrderTeam empLineOrderTeam);

    /**
     *  修改记录
     * @param empLineOrderTeam
     */
    void modifyEmpLineOrderTeam(EmpLineOrderTeam empLineOrderTeam);

    /**
     *  批量删除
     * @param ids
     */
    void removeEmpLineOrderTeam(Integer[] ids);

    List<Goods> queryGoodsIdAndName(String goodsName);
}
