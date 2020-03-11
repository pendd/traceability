package com.hvisions.mes.service.INewPdaService;

import com.hvisions.mes.domain.Material;
import com.hvisions.mes.domain.Withdrawal;
import com.hvisions.mes.domain.WithdrawalCodeRef;
import com.hvisions.mes.dto.WithdrawalCodeRefDTO;

import java.util.List;

/**
 * 退料
 *
 * @author dpeng
 * @create 2019-06-17 9:38
 */
public interface INewPdaWithdrawalService {

    int cutWithdrawalCodeRef(WithdrawalCodeRefDTO refDTO);

    /**
     *  库房退料任务列表
     * @param storeroomId
     * @return
     */
    List<Withdrawal> selectWithdrawalList(int storeroomId);

    /**
     * 一个任务对应的具体的物料明细
     * @param produceNumber
     * @param storeroomId
     * @return
     */
    List<Material> selectWithdrawalDetailList(String produceNumber,Integer storeroomId);

    /**
     *  扫二维码逻辑
     * @param code
     * @return
     */
    String selectWithdrawalLogic(String code,Integer materialId);

    /**
     *  新增退料码表记录
     * @param ref
     */
    void appendWithdrawalCodeRef(WithdrawalCodeRef ref);

    /**
     *  退料回库
     * @param produceNumber  工单号
     * @param storeroomId    库房ID
     */
    void materialBackHistory(String produceNumber,Integer storeroomId,Integer userId,Integer teamId);

    /**
     *  原料回库
     * @param list
     */
//    boolean insertMaterialBackHistory(List<MaterialStoreroomHistory> list);
}
