package com.hvisions.mes.mapper.NewPdaMapper;

import com.hvisions.mes.domain.*;
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
public interface NewPdaWithdrawalMapper {

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
    void updateWithdrawalState(@Param("produceNumber") String produceNumber, @Param("state") Integer state, @Param("materialId") Integer materialId, @Param("storeroomId") Integer storeroomId, @Param("amount")Integer amount);

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

    Goods selectGoodsByGoodsCode(String goodsCode);

    void insertProduceOrder(ProduceOrder produceOrder);

    void updateMomDetail(@Param("modId")Integer modId, @Param("cMoCode")String cMoCode);
}
