package com.hvisions.mes.mapper.NewPdaMapper;

import com.hvisions.mes.domain.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author dpeng
 * @description PDA 通知消息mapper
 * @date 2019-07-17 11:07
 */
@Mapper
@Repository
public interface NewPdaNoticeMapper {
    /**
     *  查询触发报警库存上限的物料
     * @return
     */
    List<Material> selectAlarmUpMaterial();

    /**
     *  查询触发报警库存下限的物料
     * @return
     */
    List<Material> selectAlarmDownMaterial();

    /**
     *  查询入库和回库的数据  或出库的数据
     * @param inOutType 0 入库  1 出库
     * @return
     */
    List<MaterialStoreroomHistory> selectHistory(Integer inOutType);

    /**
     *  通过物料码查询原料保质期信息
     * @param materialCode
     * @return
     */
    Material selectMaterialByCode(String materialCode);

    /**
     *  往消息表中插入数据
     * @param notice
     */
    void insertNotice(Notice notice);

    /**
     *  更新消息表
     * @param notice
     */
    void updateNotice(Notice notice);

    /**
     *  查询消息表信息 PDA做推送使用
     * @return
     */
    List<Notice> selectNotice(Integer userId);

    /**
     *  判断是否存在这一款消息
     * @param notice
     * @return
     */
    Notice selectNoticeByNotice(Notice notice);

    /**
     *  通过库房ID查询用户ID
     * @param storeroomId
     * @return
     */
    Integer selectUserIdByStoreroomId(Integer storeroomId);

    /**
     *  查询退料表中未到达并且未通知的工单号
     * @return
     */
    List<String> selectWithdrawalGroupByProduceNumber();

    Withdrawal selectWithdrawalByWorkNumber(@Param("workNumber")String workNumber);

    /**
     * 通过工单号获取工单信息
     *
     * @param workNumber
     * @return
     */
    ProduceOrder selectProduceOrderByWorkNumber(@Param("workNumber")String workNumber);

    /**
     *  修改退料超期通知为已通知
     * @param withdrawalId
     */
    void updateWithdrawalSendOrNotByWithdrawalId(@Param("withdrawalId")Integer withdrawalId);
}
