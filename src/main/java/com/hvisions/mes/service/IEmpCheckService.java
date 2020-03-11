package com.hvisions.mes.service;


import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.domain.ReportAssembling;
import com.hvisions.mes.domain.ReportElectronic;


/**
 * @author dpeng
 * @description 员工考核接口
 * @date 2019-07-31 14:05
 */
public interface IEmpCheckService {

    /**
     * 查询电子车间人员考核信息
     * @param page
     * @param empId
     * @param year    年
     * @param month   月
     * @return 考核集合
     */
    Page<ReportElectronic> queryElectronicByMonth(Page<ReportElectronic> page,Integer year,Integer month,Integer empId);

    void modifyElectronic(ReportElectronic reportElectronic);

    void modifyAssembling(ReportAssembling reportAssembling);

    /**
     * 查询装配车间人员考核信息
     * @param page
     * @param empId   员工ID
     * @param year    年
     * @param month   月
     * @return 考核集合
     */
    Page<ReportAssembling> queryAssemblingByMonth(Page<ReportAssembling> page,Integer year,Integer month,Integer empId);

}
