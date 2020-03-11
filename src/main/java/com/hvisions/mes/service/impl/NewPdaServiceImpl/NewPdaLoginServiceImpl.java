package com.hvisions.mes.service.impl.NewPdaServiceImpl;

import com.hvisions.mes.domain.Emp;
import com.hvisions.mes.domain.OrderDetail;
import com.hvisions.mes.domain.PdaInOut;
import com.hvisions.mes.domain.Schedule;
import com.hvisions.mes.mapper.NewPdaMapper.NewPdaLoginMapper;
import com.hvisions.mes.security.EncryptUtil;
import com.hvisions.mes.service.INewPdaService.INewPdaLoginService;
import com.hvisions.mes.util.DatePlusUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * @author dpeng
 * @create 2019-05-21 15:54
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class NewPdaLoginServiceImpl implements INewPdaLoginService {

    @Autowired
    private NewPdaLoginMapper loginMapper;

    /**
     *  通过用户账号获取用户信息
     * @param emp
     * @return
     */
    @Override
    public Emp selectEmpByAccount(Emp emp) {
        List<Emp> empList = loginMapper.selectEmpByAccount(emp.getAccount());

        if (empList.isEmpty()) {
            return null;
        }

        Emp returnEmp = empList.get(0);

        try {
            for (Emp getEmp : empList) {
                if (getEmp.getTeamId() != null) {
                    Schedule schedule = loginMapper.selectScheduleByTeamId(getEmp.getTeamId());
                    if (schedule != null && DatePlusUtil.compareDate(schedule.getStartTime(),schedule.getEndTime())) {
                        returnEmp = getEmp;
                        // 添加工序信息
                        if (returnEmp.getEmpId() != null && returnEmp.getTeamId() != null) {
                            List<OrderDetail> orders = loginMapper.selectOrderByEmpId(returnEmp.getEmpId(),returnEmp.getTeamId());
                            returnEmp.setOrders(orders);
                        }
                        break;
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // 对密码进行解密操作
        if (returnEmp.getPassword() != null) {
            returnEmp.setPassword(EncryptUtil.decrypt(returnEmp.getPassword()));
        }

        return returnEmp;
    }

    /**
     *  修改员工登录状态
     * @param empId
     * @param isOnline  0 不在线  1 在线
     */
    @Override
    public int modifyEmpOnlineState(Integer empId, Integer isOnline) {

        int flag = 0;

        // 通过员工ID和时间判断上一次登录状态
        PdaInOut pdaInOut = loginMapper.selectPdaInOutByEmpId(empId);

        // 判断isOnline 是登录还是登出
        if (isOnline == 1) {
            // 登录
            // 判断上一次是否有登出  没有提示不让登录
            if (pdaInOut != null && pdaInOut.getOutTime() == null) {
                flag = 1;
            }else {
                // 新增登录记录
                PdaInOut pda = new PdaInOut();
                pda.setId(null);
                pda.setOutTime(null);
                pda.setInTime(new Date());
                pda.setInOutDate(new Date());
                pda.setEmpId(empId);
                pda.setLineId(loginMapper.selectLastestLineIdByEmpId(empId));
                loginMapper.insertPdaInOut(pda);
            }
        }else if (isOnline == 0){
            // 登出 意味着一定有登录时间
            // 添加登出时间
            loginMapper.updatePdaInOut(pdaInOut.getId(),new Date());
        }

        if (flag == 0) {
            // 修改员工表登录状态
            loginMapper.updateEmpOnlineState(empId,isOnline);
        }
        return flag;
    }

    /**
     *  加密
     * @param password 传入的字符串
     * @return         加密后的字符串
     */
    @Override
    public String encryptPassword(String password) {
        return EncryptUtil.encrypt(password);
    }

}
