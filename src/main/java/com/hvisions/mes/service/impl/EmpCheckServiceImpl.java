package com.hvisions.mes.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.hvisions.mes.domain.ReportAssembling;
import com.hvisions.mes.domain.ReportElectronic;
import com.hvisions.mes.mapper.EmpCheckMapper;
import com.hvisions.mes.service.IEmpCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;


/**
 * @author dpeng
 * @description
 * @date 2019-07-31 14:06
 */
@Service
public class EmpCheckServiceImpl implements IEmpCheckService {

    @Autowired
    private EmpCheckMapper checkMapper;

    /**
     * 查询电子车间人员考核信息
     * @param page
     * @param empId  员工ID
     * @param year    年
     * @param month   月
     * @return 考核集合
     */
    @Override
    public Page<ReportElectronic> queryElectronicByMonth(Page<ReportElectronic> page, Integer year, Integer month, Integer empId) {
        Integer lineId = checkMapper.selectLineIdByEmpId(empId);
        if (lineId == null) {
            return null;
        }
        List<ReportElectronic> data = checkMapper.selectElectronicByMonth(page, year, month, lineId);
        // 如果为null  代表该月的考核表还没有记录 给页面显示所有姓名 以供录入
        if (data.isEmpty()) {
            List<ReportElectronic> reportElectronics = checkMapper.selectElectronicByLineId(page, lineId);

            reportElectronics.removeIf(e -> Objects.equals(e.getEmpId(),empId));

            // 往电子车间人员考核表中新增数据
            reportElectronics.forEach(e -> {
                e.setIyear(year);
                e.setImonth(month);
                checkMapper.insertElectronic(e);
            });

            page.setRecords(checkMapper.selectElectronicByMonth(page, year, month, lineId));

        }else {
            page.setRecords(data);
        }
        return page;
    }

    /**
     *  修改电子车间人员考核信息
     * @param rep
     */
    @Override
    public void modifyElectronic(ReportElectronic rep) {
        BigDecimal score = rep.getS5().add(rep.getDiscipline()).add(rep.getQuality()).add(rep.getEfficiency()).add(rep.getOther()).add(rep.getManage());
        rep.setScore(score);
        checkMapper.updateElectronic(rep);
    }

    /**
     *  修改装配车间人员考核信息
     * @param rep
     */
    @Override
    public void modifyAssembling(ReportAssembling rep) {
        BigDecimal score = rep.getAttitude().add(rep.getSkill()).add(rep.getAchievements()).add(rep.getCooperation()).add(rep.getResponsibility()).add(rep.getCharacter())
                .add(rep.getQuality()).add(rep.getSafeProduction()).add(rep.getManage()).add(rep.getLead()).add(rep.getResult());
        rep.setScore(score);
        checkMapper.updateAssembling(rep);
    }

    /**
     * 查询装配车间人员考核信息
     * @param page
     * @param empId   员工ID
     * @param year    年
     * @param month   月
     * @return 考核集合
     */
    @Override
    public Page<ReportAssembling> queryAssemblingByMonth(Page<ReportAssembling> page, Integer year, Integer month, Integer empId) {
        Integer lineId = checkMapper.selectLineIdByEmpId(empId);
        if (lineId == null) {
            return null;
        }
        List<ReportAssembling> data = checkMapper.selectAssemblingByMonth(page, year, month, lineId);
        // 如果为null  代表该月的考核表还没有记录 给页面显示所有姓名 以供录入
        if (data.isEmpty()) {
            List<ReportAssembling> reportElectronics = checkMapper.selectAssemblingByLineId(page, lineId);

            reportElectronics.removeIf(e -> Objects.equals(e.getEmpId(),empId));

            // 往电子车间人员考核表中新增数据
            reportElectronics.forEach(e -> {
                e.setIyear(year);
                e.setImonth(month);
                checkMapper.insertAssembling(e);
            });

            page.setRecords(checkMapper.selectAssemblingByMonth(page, year, month, lineId));

        }else {
            page.setRecords(data);
        }
        return page;
    }
}
