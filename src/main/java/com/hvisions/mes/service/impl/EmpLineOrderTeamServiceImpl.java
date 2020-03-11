package com.hvisions.mes.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.domain.EmpLineOrderTeam;
import com.hvisions.mes.domain.Goods;
import com.hvisions.mes.mapper.EmpLineOrderTeamMapper;
import com.hvisions.mes.service.IEmpLineOrderTeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 员工产线工序班组
 * @author dpeng
 * @create 2019-06-27 10:17
 */
@Service
public class EmpLineOrderTeamServiceImpl implements IEmpLineOrderTeamService {

    @Autowired
    private EmpLineOrderTeamMapper empLineOrderTeamMapper;

    /**
     *  查询员工产线工序班组配置信息
     * @param empLineOrderTeam 配置对象    可能出现值 empName lineName orderName teamName
     * @return
     */
    @Override
    public Page<EmpLineOrderTeam> queryAllEmpLineOrderTeam(Page<EmpLineOrderTeam>page,EmpLineOrderTeam empLineOrderTeam) {
        page.setRecords(empLineOrderTeamMapper.selectAllEmpLineOrderTeam(page,empLineOrderTeam));
        return page;
    }

    /**
     *  新增记录
     * @param empLineOrderTeam
     */
    @Override
    public void addEmpLineOrderTeam(EmpLineOrderTeam empLineOrderTeam) {
        empLineOrderTeamMapper.insertEmpLineOrderTeam(empLineOrderTeam);
    }

    /**
     *  修改记录
     * @param empLineOrderTeam
     */
    @Override
    public void modifyEmpLineOrderTeam(EmpLineOrderTeam empLineOrderTeam) {
        empLineOrderTeamMapper.updateEmpLineOrderTeam(empLineOrderTeam);
    }

    /**
     *  批量删除
     * @param ids
     */
    @Override
    public void removeEmpLineOrderTeam(Integer[] ids) {
        empLineOrderTeamMapper.deleteEmpLineOrderTeam(ids);
    }

    @Override
    public List<Goods> queryGoodsIdAndName(String goodsName) {
        return empLineOrderTeamMapper.selectGoodsIdAndName(goodsName == null || Objects.equals(goodsName,"") ? "" : goodsName.trim());
    }
}
