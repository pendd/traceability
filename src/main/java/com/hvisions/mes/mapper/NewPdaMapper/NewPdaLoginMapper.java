package com.hvisions.mes.mapper.NewPdaMapper;

import com.hvisions.mes.domain.Emp;
import com.hvisions.mes.domain.OrderDetail;
import com.hvisions.mes.domain.PdaInOut;
import com.hvisions.mes.domain.Schedule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface NewPdaLoginMapper {

    /**
     *  通过员工账号获取员工信息
     * @param account
     * @return
     */
    List<Emp> selectEmpByAccount(String account);

    /**
     *  通过班组ID查询排班信息
     * @param teamId
     * @return
     */
    Schedule selectScheduleByTeamId(Integer teamId);

    /**
     * 通过员工ID 获取员工的工序信息
     *
     * @param empId
     * @return
     */
    List<OrderDetail> selectOrderByEmpId(@Param("empId")Integer empId, @Param("teamId")Integer teamId);

    /**
     *  修改员工登录状态
     * @param empId      员工ID
     * @param isOnline   是否在线  0 不在线  1 在线
     */
    void updateEmpOnlineState(@Param("empId") Integer empId,@Param("isOnline")Integer isOnline);

    /**
     *  新增PDA 登入登出记录
     * @param pdaInOut
     */
    void insertPdaInOut(PdaInOut pdaInOut);

    /**
     * 修改登录登出表
     *
     * @param id      主键ID
     * @param outTime 退出时间
     */
    void updatePdaInOut(@Param("id")Integer id, @Param("outTime")Date outTime);

    /**
     *  通过员工ID和时间判断上一次登录状态
     * @param empId          员工ID
     * @return               员工登录登出记录
     */
    PdaInOut selectPdaInOutByEmpId(Integer empId);

    Integer selectLastestLineIdByEmpId(@Param("empId")Integer empId);
}
