package com.hvisions.mes.mapper;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.hvisions.mes.domain.ReportAssembling;
import com.hvisions.mes.domain.ReportElectronic;import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author dpeng
 * @description 员工考核mapper
 * @date 2019-07-31 14:05
 */
public interface EmpCheckMapper {

    /**
     * 查询电子车间人员考核信息
     * @param page
     * @param lineId  产线ID
     * @param year    年
     * @param month   月
     * @return 考核集合
     */
    List<ReportElectronic> selectElectronicByMonth(Pagination page, @Param("year") Integer year, @Param("month") Integer month, @Param("lineId")Integer lineId);

    /**
     *  查询所有人的姓名
     * @param page
     * @param lineId
     * @return
     */
    List<ReportElectronic> selectElectronicByLineId(Pagination page, @Param("lineId")Integer lineId);

    /**
     * 通过员工ID获取产线ID
     * @param empId
     * @return
     */
    Integer selectLineIdByEmpId(Integer empId);

    void insertElectronic(ReportElectronic reportElectronic);

    void updateElectronic(ReportElectronic reportElectronic);

    /**
     * 查询装配车间人员考核信息
     * @param page
     * @param lineId  产线ID
     * @param year    年
     * @param month   月
     * @return 考核集合
     */
    List<ReportAssembling> selectAssemblingByMonth(Pagination page, @Param("year") Integer year, @Param("month") Integer month, @Param("lineId")Integer lineId);

    void insertAssembling(ReportAssembling reportAssembling);

    void updateAssembling(ReportAssembling reportAssembling);

    /**
     *  查询所有人的姓名
     * @param page
     * @param lineId
     * @return
     */
    List<ReportAssembling> selectAssemblingByLineId(Pagination page, @Param("lineId")Integer lineId);
}
