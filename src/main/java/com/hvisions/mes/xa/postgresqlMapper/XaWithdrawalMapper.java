package com.hvisions.mes.xa.postgresqlMapper;

import com.hvisions.mes.domain.Material;
import com.hvisions.mes.domain.MaterialStoreroomHistory;
import com.hvisions.mes.domain.Withdrawal;
import com.hvisions.mes.domain.WithdrawalCodeRef;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 退料
 *
 * @author dpeng
 * @create 2019-06-17 9:36
 */

@Mapper
@Repository
public interface XaWithdrawalMapper {

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
    List<Material> selectWithdrawalDetailList(@Param("produceNumber") String produceNumber, @Param("storeroomId") Integer storeroomId);

    /**
     *  原料回库
     * @param list
     */
    void insertMaterialBackHistory(List<MaterialStoreroomHistory> list);

    /**
     * 更新原料到达状态
     */
    void updateWithdrawalState(@Param("state") Integer state, @Param("withdrawalId")Integer withdrawalId);

    /**
     *  查询原料ID 通过原料二维码
     * @param materialCode   原料二维码
     * @return
     */
    Withdrawal selectMaterialIdByMaterialCode(String materialCode);

    /**
     *  新增退料表记录
     * @param withdrawal
     */
    void insertWithdrawal(List<Withdrawal> withdrawal);

    /**
     *  通过库房ID查询库房名称
     * @param storeroomId
     * @return
     */
    String selectStoreroomNameByStoreroomId(Integer storeroomId);

    /**
     *  新增退料码表记录
     * @param ref
     */
    void insertWithdrawalCodeRef(WithdrawalCodeRef ref);

    /**
     *  通过退料表ID查询数量
     * @param withdrawalId
     * @return
     */
    int selectSumFromWithdrawalCodeRefByWithdrawalId(Integer withdrawalId);

    /**
     *  通过退料表ID查询所有退料码表记录
     * @param withdrawalId
     * @return
     */
    List<WithdrawalCodeRef> selectWithdrawalCodeRef(Integer withdrawalId);

    /**
     *  查询最大ID
     * @return
     */
    int selectMaxIdFromRdrecords11();

    /**
     *  删除退料扫码记录
     * @param ref
     * @return
     */
    int deleteWithdrawalCodeRef(WithdrawalCodeRef ref);
}
