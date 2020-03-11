package com.hvisions.mes.service.INewPdaService;

import com.hvisions.mes.domain.Emp;
import com.hvisions.mes.domain.PdaInOut;

/**
 * PDA端登录 查询
 *
 * @author dpeng
 * @create 2019-05-21 15:52
 */
public interface INewPdaLoginService {

    /**
     *  通过用户账号查找用户
     * @param emp
     * @return
     */
    Emp selectEmpByAccount(Emp emp);

    /**
     *  修改员工登录状态
     * @param empId
     * @param isOnline
     */
    int modifyEmpOnlineState(Integer empId,Integer isOnline);

    /**
     *  加密
     * @param password 传入的字符串
     * @return         加密后的字符串
     */
    String encryptPassword(String password);
}
