package com.hvisions.mes.mapper;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.hvisions.mes.domain.EmpLineOrderTeam;
import com.hvisions.mes.domain.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 员工产线工序班组配置
 *
 * @author dpeng
 * @create 2019-06-27 10:06
 */
@Mapper
@Repository
public interface EmpLineOrderTeamMapper {

    /**
     *  查询员工产线工序班组配置信息
     * @param empLineOrderTeam 配置对象    可能出现值 empName lineName orderName teamName
     * @return
     */
    List<EmpLineOrderTeam> selectAllEmpLineOrderTeam(Pagination page,EmpLineOrderTeam empLineOrderTeam);

    /**
     *  新增记录
     * @param empLineOrderTeam
     */
    void insertEmpLineOrderTeam(EmpLineOrderTeam empLineOrderTeam);

    /**
     *  修改记录
     * @param empLineOrderTeam
     */
    void updateEmpLineOrderTeam(EmpLineOrderTeam empLineOrderTeam);

    /**
     *  批量删除
     * @param ids
     */
    void deleteEmpLineOrderTeam(Integer[] ids);

    Integer selectLineIdByUserId(Integer userId);

    List<Goods> selectGoodsIdAndName(@Param("goodsName")String goodsName);
}
